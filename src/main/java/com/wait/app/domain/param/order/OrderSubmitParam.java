package com.wait.app.domain.param.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/10/5 2:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitParam {

    @ApiModelProperty(value = "堂食(0:外带 1:堂食)")
    private Integer dine;

    @ApiModelProperty(value = "订单明细")
    private List<OrderSubmitDetail> orderSubmitDetailList;

    @ApiModelProperty(value = "购物车id")
    private List<String> cartIds;

    @Data
    public static class OrderSubmitDetail{

        @ApiModelProperty(value = "商品名称")
        private String name;

        @ApiModelProperty(value = "商品id")
        private String goodsId;

        @ApiModelProperty(value = "单价")
        private BigDecimal price;

        @ApiModelProperty(value = "数量")
        private Integer number;


    }

}
