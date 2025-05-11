package com.wait.app.domain.param.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 *
 * @author 天
 * Time: 2025/4/23 13:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSaveParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "数量")
    private Integer number;
}
