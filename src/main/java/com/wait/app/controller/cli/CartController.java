package com.wait.app.controller.cli;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.cart.CartListDTO;
import com.wait.app.domain.param.cart.CartSaveParam;
import com.wait.app.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 天
 * Time: 2025/4/23 5:48
 */
@RestController
@Api(tags = "购物车",value = "购物车")
@RequestMapping("/cart")
@SaIgnore
public class CartController extends BaseController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存购物车")
    public SaResult save(@RequestBody CartSaveParam cartSaveParam){
        cartService.save(cartSaveParam);
        return SaResult.ok("保存成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "购物车列表")
    public SaResult list(){
        List<CartListDTO> list =  cartService.list();
        return SaResult.data(list);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除购物车")
    public SaResult delete(@RequestParam String cartId){
        cartService.delete(cartId);
        return SaResult.ok("删除成功");
    }

}
