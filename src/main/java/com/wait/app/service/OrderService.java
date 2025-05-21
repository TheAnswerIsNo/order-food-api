package com.wait.app.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.wait.app.domain.dto.order.OrderListDTO;
import com.wait.app.domain.dto.wechatPayCallback.DecryptedSuccessData;
import com.wait.app.domain.entity.*;
import com.wait.app.domain.enumeration.AttachmentEnum;
import com.wait.app.domain.enumeration.OrderStatusEnum;
import com.wait.app.domain.enumeration.PrefixEnum;
import com.wait.app.domain.enumeration.WeChatInfoEnum;
import com.wait.app.domain.param.order.OrderListParam;
import com.wait.app.domain.param.order.OrderSubmitParam;
import com.wait.app.repository.CartRepository;
import com.wait.app.repository.OrderDetailRepository;
import com.wait.app.repository.OrderRepository;
import com.wait.app.utils.page.PageUtil;
import com.wait.app.utils.page.ResponseDTOWithPage;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 *
 * @author 天
 * Time: 2024/10/5 2:43
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private Config payConfig;

    private final RedisTemplate<String, Object> objectRedisTemplate;

    private final CartRepository cartRepository;

    private final AttachmentService attachmentService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, RedisTemplate<String, Object> objectRedisTemplate, CartRepository cartRepository, AttachmentService attachmentService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.objectRedisTemplate = objectRedisTemplate;
        this.cartRepository = cartRepository;
        this.attachmentService = attachmentService;
    }

    /**
     * 提交订单
     * @param orderSubmitParam orderSubmitParam
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderListDTO submit(OrderSubmitParam orderSubmitParam) {
        // 删除购物车
        cartRepository.lambdaUpdate().in(Cart::getId,orderSubmitParam.getCartIds()).remove();
        // 1.创建订单参数
        // 生成唯一订单号 今天的订单数据库已存在订单号+1
        Integer orderNo = generateOrderNo();
        TOrder order = TOrder
                .builder()
                .orderNo(orderNo)
                .dine(orderSubmitParam.getDine())
                .build();
        orderRepository.save(order);

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<Integer> totalNumber = new AtomicReference<>(0);

        List<OrderSubmitParam.OrderSubmitDetail> orderSubmitDetailList = orderSubmitParam.getOrderSubmitDetailList();
        List<OrderDetail> orderDetails = BeanUtil.copyToList(orderSubmitDetailList, OrderDetail.class);
        orderDetails.forEach(item -> {
            item.setOrderId(order.getId());
            totalPrice.set(totalPrice.get().add(item.getPrice().multiply(new BigDecimal(item.getNumber()))));
            totalNumber.updateAndGet(v -> v + item.getNumber());
        });
        orderDetailRepository.saveBatch(orderDetails);

        order.setTotalNumber(totalNumber.get());
        order.setTotalPrice(totalPrice.get());
        orderRepository.updateById(order);

        //TODO 2.请求微信预下单接口获取支付参数
//        PrepayWithRequestPaymentResponse prepayOrder = createPrepayOrder(order, user.getOpenId());
        // 设置订单时间15分钟
        objectRedisTemplate.opsForValue().set(PrefixEnum.ORDER.getValue() + order.getId(),new PrepayWithRequestPaymentResponse(), Duration.ofMinutes(15));

        OrderListDTO orderListDTO = new OrderListDTO();
        BeanUtil.copyProperties(order,orderListDTO);
        List<OrderListDTO.OrderListDetail> orderListDetailList = BeanUtil.copyToList(orderDetails, OrderListDTO.OrderListDetail.class);
        List<String> goodsIdList = orderListDetailList.stream().map(OrderListDTO.OrderListDetail::getGoodsId).toList();

        // 设置图片
        Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIdList)
                .stream().collect(Collectors.groupingBy(Attachment::getOwnerId));
        orderListDetailList.forEach(item -> {
            List<Attachment> attachmentList = attachmentMap.getOrDefault(item.getGoodsId(), List.of());
            if (CollUtil.isNotEmpty(attachmentList)){
                List<String> photos = new ArrayList<>();
                for (Attachment attachment : attachmentList) {
                    String url = attachmentService.getAttachmentUrl(attachment.getObjName(), null);
                    photos.add(url);
                }
                item.setPhotos(photos);
            }
        });
        orderListDTO.setOrderListDetailList(orderListDetailList);
        return orderListDTO;
    }

    /**
     * 生成唯一订单号
     * @return 订单号
     */
    private synchronized Integer generateOrderNo() {
        // 查询今天的订单对订单号拍倒叙
        LocalDate now = LocalDate.now();
        List<TOrder> orders = orderRepository.lambdaQuery().like(TOrder::getCreateTime, now).orderByDesc(TOrder::getOrderNo).list();
        if (CollUtil.isEmpty(orders)) {
            return 1;
        }
        TOrder order = orders.get(0);
        Integer orderNo = order.getOrderNo();
        return orderNo + 1;
    }

    private PrepayWithRequestPaymentResponse createPrepayOrder(TOrder order, String openId) {

        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(payConfig).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        BigDecimal total = order.getTotalPrice().multiply(new BigDecimal(100));
        amount.setTotal(total.intValueExact());
        request.setAmount(amount);
        request.setAppid(WeChatInfoEnum.APPID.getValue());
        request.setMchid(WeChatInfoEnum.MERCHANT_ID.getValue());
        request.setDescription("肯德基商品");
        request.setNotifyUrl(WeChatInfoEnum.NOTIFYURL.getValue());
        request.setOutTradeNo(order.getId());
        Payer payer = new Payer();
        payer.setOpenid(openId);
        request.setPayer(payer);
        // response包含了调起支付所需的所有参数，可直接用于前端调起支付
        return service.prepayWithRequestPayment(request);
    }

    /**
     * 取消订单
     * @param orderId orderId
     */
    public void cancelOrder(String orderId) {
        String orderKey = PrefixEnum.ORDER.getValue() + orderId;
        objectRedisTemplate.delete(orderKey);

        // 关闭订单
//        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(payConfig).build();
//        CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
//        closeOrderRequest.setOutTradeNo(orderId);
//        closeOrderRequest.setMchid(WeChatInfoEnum.MERCHANT_ID.getValue());
//        service.closeOrder(closeOrderRequest);

        TOrder order = TOrder.builder().id(orderId).status(OrderStatusEnum.DDQX.getValue()).build();
        orderRepository.updateById(order);
    }

    /**
     * 吊起wx支付
     * @param orderId orderId
     */
    public Integer payOrder(String orderId) {
        // TODO 付款
        // 修改订单状态
        TOrder order = TOrder.builder()
                .id(orderId)
                .status(OrderStatusEnum.ZZZ.getValue())
                .build();
        orderRepository.updateById(order);

        String orderKey = PrefixEnum.ORDER.getValue() + orderId;
        objectRedisTemplate.delete(orderKey);

//        String orderKey = PrefixEnum.ORDER.getValue() + orderId;
//        RBucket<Object> bucket = redissonClient.getBucket(orderKey);
//        String prepayResponse = (String)bucket.get();
//        return JSONUtil.toBean(prepayResponse, PrepayWithRequestPaymentResponse.class);
        return order.getOrderNo();
    }

    /**
     * 支付回调
     * @param decryptedSuccessData decryptedSuccessData
     */
    public void payCallback(DecryptedSuccessData decryptedSuccessData) {
        String orderId = decryptedSuccessData.getOutTradeNo();
        // 修改订单状态
        TOrder order = TOrder.builder()
                .id(orderId)
//                .status(OrderStatusEnum.SHZ.getValue())
                .build();
        orderRepository.updateById(order);
    }

    /**
     * 订单列表
     * @param orderListParam orderListParam
     */
    public ResponseDTOWithPage<OrderListDTO> list(OrderListParam orderListParam) {
        PageUtil.startPage(orderListParam,true,TOrder.class);
        List<TOrder> orderList = orderRepository.lambdaQuery()
                .eq(ObjUtil.isNotEmpty(orderListParam.getStatus()), TOrder::getStatus, orderListParam.getStatus())
                .orderByDesc(TOrder::getCreateTime)
                .list();
        if (CollUtil.isEmpty(orderList)) {
            return new ResponseDTOWithPage<>(List.of(),0L);
        }
        List<OrderListDTO> list = BeanUtil.copyToList(orderList, OrderListDTO.class);
        ResponseDTOWithPage<OrderListDTO> result = PageUtil.getListDTO(list, orderListParam);
        List<String> orderIds = orderList.stream().map(TOrder::getId).toList();
        // 完善订单详情
        List<OrderDetail> orderDetails = orderDetailRepository.lambdaQuery()
                .in(OrderDetail::getOrderId, orderIds)
                .list();
        Map<String, List<OrderDetail>> orderDetailMap = orderDetails.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));

        // 添加图片
        // 获取商品ids
        List<String> goodsIds = orderDetails.stream().map(OrderDetail::getGoodsId).toList();
        Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIds)
                .stream().collect(Collectors.groupingBy(Attachment::getOwnerId));
        result.getList().forEach(item -> {
            List<OrderListDTO.OrderListDetail> orderListDetails = new ArrayList<>();
            List<OrderDetail> orderDetailList = orderDetailMap.get(item.getId());
            for (OrderDetail orderDetail : orderDetailList) {
                OrderListDTO.OrderListDetail orderListDetail = BeanUtil.copyProperties(orderDetail, OrderListDTO.OrderListDetail.class);
                List<Attachment> attachmentList = attachmentMap.getOrDefault(orderListDetail.getGoodsId(), List.of());
                if (CollUtil.isNotEmpty(attachmentList)){
                    List<String> photos = new ArrayList<>();
                    for (Attachment attachment : attachmentList) {
                        String url = attachmentService.getAttachmentUrl(attachment.getObjName(), null);
                        photos.add(url);
                    }
                    orderListDetail.setPhotos(photos);
                }
                orderListDetails.add(orderListDetail);
            }
            item.setOrderListDetailList(orderListDetails);
        });

        return result;
    }

    /**
     * 完成订单
     * @param orderId orderId
     */
    public void complete(String orderId) {
        // 修改订单状态
        orderRepository.lambdaUpdate()
                .eq(TOrder::getStatus,OrderStatusEnum.ZZZ.getValue())
               .eq(TOrder::getId,orderId)
               .set(TOrder::getStatus,OrderStatusEnum.YWC.getValue())
               .update();
    }

    /**
     * 客户端订单列表
     * @return list
     */
    public List<OrderListDTO> cliList() {
        List<TOrder> orderList = orderRepository.lambdaQuery()
                .orderByDesc(TOrder::getCreateTime)
                .list();
        if (CollUtil.isEmpty(orderList)) {
            return List.of();
        }
        List<OrderListDTO> list = BeanUtil.copyToList(orderList, OrderListDTO.class);
        List<String> orderIds = list.stream().map(OrderListDTO::getId).toList();
        // 完善订单详情
        List<OrderDetail> orderDetails = orderDetailRepository.lambdaQuery()
                .in(OrderDetail::getOrderId, orderIds)
                .list();
        Map<String, List<OrderDetail>> orderDetailMap = orderDetails.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));

        // 添加图片
        // 获取商品ids
        List<String> goodsIdList = orderDetails.stream().map(OrderDetail::getGoodsId).distinct().collect(Collectors.toList());
        Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIdList)
                .stream().collect(Collectors.groupingBy(Attachment::getOwnerId));
        list.forEach(item -> {
            List<OrderListDTO.OrderListDetail> orderListDetails = new ArrayList<>();
            List<OrderDetail> orderDetailList = orderDetailMap.get(item.getId());
            for (OrderDetail orderDetail : orderDetailList) {
                OrderListDTO.OrderListDetail orderListDetail = BeanUtil.copyProperties(orderDetail, OrderListDTO.OrderListDetail.class);
                List<Attachment> attachmentList = attachmentMap.getOrDefault(orderListDetail.getGoodsId(), List.of());
                if (CollUtil.isNotEmpty(attachmentList)){
                    List<String> photos = new ArrayList<>();
                    for (Attachment attachment : attachmentList) {
                        String url = attachmentService.getAttachmentUrl(attachment.getObjName(), null);
                        photos.add(url);
                    }
                    orderListDetail.setPhotos(photos);
                }
                orderListDetails.add(orderListDetail);
            }
            item.setOrderListDetailList(orderListDetails);
        });

        return list;
    }

    /**
     * 获取制作中订单数量
     * @return number
     */
    public Integer productionNumber() {
        Long count = orderRepository.lambdaQuery().eq(TOrder::getStatus, OrderStatusEnum.ZZZ.getValue()).count();
        return count.intValue();
    }
}
