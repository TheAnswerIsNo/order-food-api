package com.wait.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tangzc.autotable.annotation.AutoColumn;
import com.tangzc.autotable.annotation.AutoTable;
import com.tangzc.autotable.annotation.enums.DefaultValueEnum;
import com.tangzc.mpe.annotation.InsertFillTime;
import com.tangzc.mpe.annotation.InsertUpdateFillTime;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * @author 天
 * Time: 2024/9/10 10:38
 */
@Data
@AutoTable(comment = "字典表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dict {

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "父级id", notNull = true,defaultValueType = DefaultValueEnum.EMPTY_STRING,length = 45)
    private String parentId;

    @AutoColumn(comment = "排序",notNull = true,defaultValue = "0",length = 10)
    private Integer sort;

    @AutoColumn(comment = "标签",notNull = true,defaultValueType = DefaultValueEnum.EMPTY_STRING,length = 50)
    private String label;

    @AutoColumn(comment = "类型",notNull = true)
    private String type;

    @AutoColumn(comment = "备注")
    private String remark;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}

