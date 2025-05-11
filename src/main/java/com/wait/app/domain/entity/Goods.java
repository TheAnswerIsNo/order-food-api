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
 *
 * @author 天
 * Time: 2024/9/10 14:25
 */
@Data
@AutoTable(comment = "商品表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods{

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "名称",notNull = true)
    private String name;

    @AutoColumn(comment = "所属类别",notNull = true,length = 45)
    private String dictId;

    @AutoColumn(comment = "单价",notNull = true)
    private BigDecimal price;

    @AutoColumn(comment = "描述")
    private String description;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}