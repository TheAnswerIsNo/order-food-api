package com.wait.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tangzc.autotable.annotation.AutoColumn;
import com.tangzc.autotable.annotation.AutoTable;
import com.tangzc.mpe.annotation.InsertFillTime;
import com.tangzc.mpe.annotation.InsertUpdateFillTime;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 天
 * Time: 2024/9/11 16:24
 */
@Data
@AutoTable(comment = "订单表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TOrder {

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "订单编号",notNull = true,length = 45)
    private Integer orderNo;

    @AutoColumn(comment = "总价",notNull = true,defaultValue = "0")
    private BigDecimal totalPrice;

    @AutoColumn(comment = "总数量",notNull = true,defaultValue = "0")
    private Integer totalNumber;

    @AutoColumn(comment = "是否堂食(0:外带 1:堂食)",notNull = true,defaultValue = "1")
    private Integer dine;

    @AutoColumn(comment = "订单状态(0:未付款 1:制作中 2:已完成 3:订单取消)",notNull = true,defaultValue = "0")
    private Integer status;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}
