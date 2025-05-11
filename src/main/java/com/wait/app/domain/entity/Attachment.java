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
 * Time: 2024/9/7 9:37
 */
@Data
@AutoTable(comment = "附件表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "相关表名",notNull = true,length = 15)
    private String ownerType;

    @AutoColumn(comment = "相关id",notNull = true,length = 45)
    private String ownerId;

    @AutoColumn(comment = "附件",notNull = true,length = 255)
    private String name;

    @AutoColumn(comment = "对象存储唯一标识",notNull = true,length = 255)
    private String objName;

    @AutoColumn(comment = "附件相对路径",notNull = true,length = 255)
    private String path;

    @AutoColumn(comment = "附件类型",notNull = true,length = 255)
    private String type;

    @AutoColumn(comment = "附件大小",notNull = true)
    private Long size;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}
