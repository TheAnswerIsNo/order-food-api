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

import java.time.LocalDateTime;

/**
 *
 * @author 天
 * Time: 2024/9/10 14:47
 */
@Data
@AutoTable(comment = "角色表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "名称",notNull = true)
    private String name;

    @AutoColumn(comment = "描述")
    private String description;

    @AutoColumn(comment = "排序",notNull = true,defaultValue = "0",length = 10)
    private Integer sort;

    @AutoColumn(comment = "启用(0:禁用 1:启用)",notNull = true,defaultValue = "1",length = 10)
    private Integer enabled;

    @AutoColumn(comment = "roleKey(user:普通用户,admin:管理员)",notNull = true,defaultValue = "user")
    private String roleKey;

    @AutoColumn(comment = "创建人id",notNull = true,length = 45)
    private String creatorId;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}
