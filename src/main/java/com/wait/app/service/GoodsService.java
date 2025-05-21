package com.wait.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wait.app.domain.dto.goods.GoodsListDTO;
import com.wait.app.domain.entity.Attachment;
import com.wait.app.domain.entity.Goods;
import com.wait.app.domain.entity.GoodsDict;
import com.wait.app.domain.entity.OrderDetail;
import com.wait.app.domain.enumeration.AttachmentEnum;
import com.wait.app.domain.param.goods.GoodsListParam;
import com.wait.app.domain.param.goods.GoodsSaveParam;
import com.wait.app.repository.DictRepository;
import com.wait.app.repository.GoodsDictRepository;
import com.wait.app.repository.GoodsRepository;
import com.wait.app.repository.OrderDetailRepository;
import com.wait.app.utils.page.PageUtil;
import com.wait.app.utils.page.ResponseDTOWithPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author 天
 * Time: 2025/4/23 13:47
 */
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    private final AttachmentService attachmentService;

    private final GoodsDictRepository goodsDictRepository;

    private final DictRepository dictRepository;

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository, AttachmentService attachmentService, GoodsDictRepository goodsDictRepository, DictRepository dictRepository, OrderDetailRepository orderDetailRepository) {
        this.goodsRepository = goodsRepository;
        this.attachmentService = attachmentService;
        this.goodsDictRepository = goodsDictRepository;
        this.dictRepository = dictRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public ResponseDTOWithPage<GoodsListDTO> list(GoodsListParam goodsListParam) {
        // 查询关联表获取所有的goodsId
        List<GoodsDict> goodsDictList = goodsDictRepository.lambdaQuery()
                .eq(StrUtil.isNotBlank(goodsListParam.getDictId()),GoodsDict::getDictId, goodsListParam.getDictId()).list();
        Set<String> allGoodsIds = goodsDictList.stream().map(GoodsDict::getGoodsId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(allGoodsIds)){
            return new ResponseDTOWithPage<>(List.of(),0L);
        }
        PageUtil.startPage(goodsListParam,true, Goods.class);
        // 获取所有的goods
        List<Goods> list = goodsRepository.lambdaQuery()
                .in(Goods::getId, allGoodsIds)
                .orderByDesc(Goods::getCreateTime)
                .list();
        if (CollUtil.isEmpty(list)){
            return new ResponseDTOWithPage<>(List.of(),0L);
        }
        List<GoodsListDTO> goodsList = BeanUtil.copyToList(list, GoodsListDTO.class);
        // 添加图片
        List<String> goodsIds = goodsList.stream().map(GoodsListDTO::getId).toList();
        // 获取所有相关的dictIds
        Map<String, List<String>> goodsDictMap = goodsDictRepository.lambdaQuery()
                .in(GoodsDict::getGoodsId, goodsIds).list()
                .stream().collect(Collectors.groupingBy(GoodsDict::getGoodsId, Collectors.mapping(GoodsDict::getDictId, Collectors.toList())));
        Map<String, List<Attachment>> photoMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIds).stream()
                .collect(Collectors.groupingBy(Attachment::getOwnerId));
        goodsList.forEach(item ->{
            item.setDictIds(goodsDictMap.getOrDefault(item.getId(), List.of()));
            List<Attachment> attachments = photoMap.getOrDefault(item.getId(), List.of());
            if (CollUtil.isNotEmpty(attachments)){
                List<String> photos = attachments.stream().map(attachment -> attachmentService.getAttachmentUrl(attachment.getObjName(), null)).toList();
                item.setPhotos(photos);
            }
        });

        return PageUtil.getListDTO(goodsList, goodsListParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String goodsId) {
        goodsRepository.removeById(goodsId);
        List<Attachment> attachment = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsId);
        if (CollUtil.isNotEmpty(attachment)){
            attachmentService.removeAttachment(AttachmentEnum.GOODS.getValue(), goodsId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(GoodsSaveParam goodsSaveParam) {
        Goods goods = BeanUtil.copyProperties(goodsSaveParam, Goods.class);
        goodsRepository.saveOrUpdate(goods);
        // 修改商品类别关联
        if (StrUtil.isNotBlank(goodsSaveParam.getId())){
            goodsDictRepository.lambdaUpdate().eq(GoodsDict::getGoodsId,goodsSaveParam.getId()).remove();
        }
        // 遍历dictIds 将dictId和goodsId保存到goods_dict表中
        List<GoodsDict> list = goodsSaveParam.getDictIds().stream()
                .map(dictId -> GoodsDict.builder().goodsId(goods.getId()).dictId(dictId).build()).toList();
        goodsDictRepository.saveBatch(list);
        if (CollUtil.isNotEmpty(goodsSaveParam.getPhotos())){
            if (StrUtil.isNotBlank(goodsSaveParam.getId())){
                attachmentService.removeAttachment(AttachmentEnum.GOODS.getValue(), goods.getId());
            }
            attachmentService.uploadAttachmentList(goodsSaveParam.getPhotos(),AttachmentEnum.GOODS.getValue(), goods.getId());
        }
    }

    public List<GoodsListDTO> cliList(String dictId) {
        // 查询关联表获取所有的goodsId
        List<GoodsDict> goodsDictList = goodsDictRepository.lambdaQuery()
                .eq(GoodsDict::getDictId, dictId).list();
        List<String> goodsIds = goodsDictList.stream().map(GoodsDict::getGoodsId).toList();
        // 获取所有的goods
        if (CollUtil.isEmpty(goodsIds)){
            return List.of();
        }
        List<Goods> goodsList = goodsRepository.lambdaQuery()
                .in(Goods::getId, goodsIds)
                .orderByDesc(Goods::getCreateTime)
                .list();
        // 封装成GoodsListDTO
        List<GoodsListDTO> goodsListDTOList = BeanUtil.copyToList(goodsList, GoodsListDTO.class);
        // 添加图片
        Map<String, List<Attachment>> photoMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIds).stream()
                .collect(Collectors.groupingBy(Attachment::getOwnerId));
        goodsListDTOList.forEach(item ->{
            List<Attachment> attachments = photoMap.getOrDefault(item.getId(), List.of());
            if (CollUtil.isNotEmpty(attachments)){
                List<String> photos = attachments.stream().map(attachment -> attachmentService.getAttachmentUrl(attachment.getObjName(), null)).toList();
                item.setPhotos(photos);
            }
        });
        // 将所有数据封装
        return goodsListDTOList;
    }

    /**
     * 获取推荐商品列表
     * @return 推荐商品列表
     */
    public List<GoodsListDTO> cliListRecommend() {
        // 获取推荐的商品id
        Set<String> goodsIds = goodsRepository.lambdaQuery()
                .eq(Goods::getIsRecommend, 1)
                .list()
                .stream()
                .map(Goods::getId)
                .collect(Collectors.toSet());
        // 订单中销量排行前5的商品id
        List<String> top5SalesGoodsIds = getTop5SalesGoodsIds();
        goodsIds.addAll(top5SalesGoodsIds);

        // 获取所有的goods
        if (CollUtil.isEmpty(goodsIds)){
            return List.of();
        }
        List<Goods> goodsList = goodsRepository.lambdaQuery()
                .in(Goods::getId, goodsIds)
                .orderByDesc(Goods::getCreateTime)
                .list();
        // 封装成GoodsListDTO
        List<GoodsListDTO> goodsListDTOList = BeanUtil.copyToList(goodsList, GoodsListDTO.class);
        // 添加图片
        Map<String, List<Attachment>> photoMap = attachmentService.getAttachment(AttachmentEnum.GOODS.getValue(), goodsIds).stream()
                .collect(Collectors.groupingBy(Attachment::getOwnerId));
        goodsListDTOList.forEach(item ->{
            List<Attachment> attachments = photoMap.getOrDefault(item.getId(), List.of());
            if (CollUtil.isNotEmpty(attachments)){
                List<String> photos = attachments.stream().map(attachment -> attachmentService.getAttachmentUrl(attachment.getObjName(), null)).toList();
                item.setPhotos(photos);
            }
        });
        return goodsListDTOList;
    }

    /**
     * 订单中销量排行前5的商品id
     * @return 订单中销量排行前5的商品id
     */
    private List<String> getTop5SalesGoodsIds() {
        QueryWrapper<OrderDetail> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.select("goods_id, SUM(goods_id) as totalQuantity")
                .groupBy("goods_id")
                .orderByDesc("totalQuantity")
                .last("LIMIT 5");
        return orderDetailRepository.getBaseMapper()
                .selectMaps(objectQueryWrapper)
                .stream()
                .map(map -> (String) map.get("goodsId"))
                .collect(Collectors.toList());
    }

    /**
     * 推荐商品
     * @param goodsId 商品id
     */
    public void recommend(String goodsId) {
        Goods goods = goodsRepository.getById(goodsId);
        if (goods.getIsRecommend() == 1){
            goods.setIsRecommend(0);
        }else {
            goods.setIsRecommend(1);
        }
        goodsRepository.updateById(goods);
    }
}
