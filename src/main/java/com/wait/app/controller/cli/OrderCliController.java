package com.wait.app.controller.cli;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.order.OrderListDTO;
import com.wait.app.domain.param.order.OrderSubmitParam;
import com.wait.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 天
 * Time: 2024/10/4 11:51
 */
@RestController
@Api(tags = "订单-客户端",value = "订单-客户端")
@RequestMapping("/order/cli")
@SaIgnore
public class OrderCliController extends BaseController {

    private final OrderService orderService;

    @Autowired
    public OrderCliController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("订单列表")
    @GetMapping(value = "/list")
    public SaResult cliList(){
        List<OrderListDTO> list = orderService.cliList();
        return SaResult.data(list);
    }

    @ApiOperation("制作中订单数量")
    @GetMapping(value = "/productionNumber")
    public SaResult productionNumber(){
        Integer productionNumber = orderService.productionNumber();
        return SaResult.data(productionNumber);
    }

    @PostMapping("/submit")
    @ApiOperation(value = "提交订单")
    public SaResult addCart(@RequestBody OrderSubmitParam orderSubmitParam){
        OrderListDTO orderListDTO = orderService.submit(orderSubmitParam);
        return SaResult.data(orderListDTO);
    }

    @ApiOperation("取消订单")
    @GetMapping(value = "/cancel/{orderId}")
    public SaResult cancelOrder(@PathVariable String orderId){
        orderService.cancelOrder(orderId);
        return SaResult.ok("取消订单成功");
    }

    @ApiOperation("付款接口")
    @GetMapping(value="/pay/{orderId}")
    public SaResult payOrder(@PathVariable String orderId){
        Integer orderNo = orderService.payOrder(orderId);
        return SaResult.data(orderNo);
    }

}
