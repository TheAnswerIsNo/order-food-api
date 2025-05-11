package com.wait.app.domain.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/10/7 18:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "总数量")
    private Integer totalNumber;

    @ApiModelProperty(value = "订单状态(0:未付款 1:送货中 2:已送达 3:已完成 4:订单取消)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "订单明细")
    private List<OrderListDetail> orderListDetailList = new ArrayList<>();

    @Data
    public static class OrderListDetail{

        @ApiModelProperty(value = "商品id")
        private String goodsId;

        @ApiModelProperty(value = "商品名称")
        private String name;

        @ApiModelProperty(value = "单价")
        private BigDecimal price;

        @ApiModelProperty(value = "数量")
        private Integer number;

        @ApiModelProperty(value = "照片")
        private List<String> photos;
    }
}
