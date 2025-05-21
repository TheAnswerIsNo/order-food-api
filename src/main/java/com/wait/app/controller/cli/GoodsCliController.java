package com.wait.app.controller.cli;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.goods.GoodsListDTO;
import com.wait.app.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author 天
 * Time: 2025/4/23 5:49
 */
@RestController
@Api(tags = "商品-客户端",value = "商品-客户端")
@RequestMapping("/goods/cli")
@SaIgnore
public class GoodsCliController extends BaseController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsCliController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "商品列表")
    @GetMapping("/list/{dictId}")
    public SaResult list(@PathVariable String dictId){
        List<GoodsListDTO> list = goodsService.cliList(dictId);
        return SaResult.data(list);
    }

    @ApiOperation(value = "商品推荐列表")
    @GetMapping("/list/recommend")
    public SaResult listRecommend(){
        List<GoodsListDTO> list = goodsService.cliListRecommend();
        return SaResult.data(list);
    }

}
