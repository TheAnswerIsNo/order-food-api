package com.wait.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tangzc.autotable.annotation.AutoColumn;
import com.tangzc.autotable.annotation.AutoTable;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.autotable.annotation.enums.IndexTypeEnum;
import com.tangzc.mpe.annotation.InsertFillTime;
import com.tangzc.mpe.annotation.InsertUpdateFillTime;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 天
 * Time: 2024/9/5 23:42
 */
@Data
@AutoTable(comment = "用户表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @ColumnId(mode = IdType.ASSIGN_UUID,comment = "id",length = 45)
    private String id;

    @AutoColumn(comment = "密码",length = 255,notNull = true)
    private String password;

    @AutoColumn(comment = "手机号",length = 11,notNull = true)
    @Index(name = "index_unique_phone",type = IndexTypeEnum.UNIQUE)
    private String phone;

    @AutoColumn(comment = "昵称")
    private String nickname;

    @InsertFillTime
    @AutoColumn(comment = "创建时间",notNull = true)
    private LocalDateTime createTime;

    @InsertUpdateFillTime
    @AutoColumn(comment = "更新时间",notNull = true)
    private LocalDateTime updateTime;
}
