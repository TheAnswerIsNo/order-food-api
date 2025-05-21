package com.wait.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wait.app.domain.dto.cart.CartListDTO;
import com.wait.app.domain.entity.Attachment;
import com.wait.app.domain.entity.Cart;
import com.wait.app.domain.enumeration.AttachmentEnum;
import com.wait.app.domain.param.cart.CartSaveParam;
import com.wait.app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 天
 * Time: 2025/4/23 13:29
 */
@Service
public class CartService {

    private final CartRepository cartRepository;

    private final AttachmentService attachmentService;

    @Autowired
    public CartService(CartRepository cartRepository, AttachmentService attachmentService) {
        this.cartRepository = cartRepository;
        this.attachmentService = attachmentService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String cartId) {
        cartRepository.removeById(cartId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(CartSaveParam cartSaveParam) {
        if (StrUtil.isNotBlank(cartSaveParam.getId())) {
            // 若 cartSaveParam 已有 ID，直接复制属性并保存或更新
            Cart cart = BeanUtil.copyProperties(cartSaveParam, Cart.class);
            cartRepository.saveOrUpdate(cart);
            return;
        }

        // 查询当前用户是否有相同 goodsId 的购物车信息
        Cart existingCart = cartRepository.lambdaQuery()
                .eq(Cart::getGoodsId, cartSaveParam.getGoodsId())
                .one();

        if (existingCart != null) {
            // 若存在相同 goodsId 的购物车信息，更新数量
            existingCart.setNumber(existingCart.getNumber() + cartSaveParam.getNumber());
            cartRepository.updateById(existingCart);
        } else {
            // 若不存在，创建新的购物车记录
            Cart newCart = BeanUtil.copyProperties(cartSaveParam, Cart.class);
            cartRepository.saveOrUpdate(newCart);
        }
    }

    public List<CartListDTO> list() {
        List<Cart> list = cartRepository.lambdaQuery().orderByDesc(Cart::getCreateTime).list();
        if (CollUtil.isEmpty(list)){
            return List.of();
        }
        List<String> goodsIds = list.stream().map(Cart::getGoodsId).toList();
        Map<String, List<Attachment>> goodsMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIds)
                .stream().collect(Collectors.groupingBy(Attachment::getOwnerId));
        List<CartListDTO> cartList = new ArrayList<>();
        list.forEach(item ->{
            CartListDTO cartListDTO = BeanUtil.copyProperties(item, CartListDTO.class);
            List<Attachment> attachments = goodsMap.get(item.getGoodsId());
            if (CollUtil.isNotEmpty(attachments)){
                List<String> photos = attachments.stream().map(Attachment::getObjName).map(objName -> attachmentService.getAttachmentUrl(objName, null)).toList();
                cartListDTO.setPhotos(photos);
            }
            cartList.add(cartListDTO);
        });

        return cartList;
    }
}
