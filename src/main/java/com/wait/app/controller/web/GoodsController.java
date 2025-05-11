package com.wait.app.controller.web;

import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.goods.GoodsListDTO;
import com.wait.app.domain.param.goods.GoodsListParam;
import com.wait.app.domain.param.goods.GoodsSaveParam;
import com.wait.app.service.GoodsService;
import com.wait.app.utils.page.ResponseDTOWithPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author 天
 * Time: 2025/4/23 5:49
 */
@RestController
@Api(tags = "商品",value = "商品")
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "商品列表")
    @GetMapping("/list")
    public SaResult list(@ModelAttribute GoodsListParam goodsListParam){
        ResponseDTOWithPage<GoodsListDTO> list = goodsService.list(goodsListParam);
        return SaResult.data(list);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除商品")
    public SaResult delete(@RequestParam String goodsId){
        goodsService.delete(goodsId);
        return SaResult.ok("删除成功");
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存购物车")
    public SaResult save(GoodsSaveParam goodsSaveParam){
        goodsService.save(goodsSaveParam);
        return SaResult.ok("保存成功");
    }


}
