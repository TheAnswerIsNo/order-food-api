package com.wait.app.domain.dto.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 天
 * Time: 2025/4/23 13:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListDTO {


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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "图片")
    private List<String> photos;


}
