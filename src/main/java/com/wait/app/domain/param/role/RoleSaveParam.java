package com.wait.app.domain.param.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author 天
 * Time: 2024/9/15 9:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "启用(0:禁用 1:启用)")
    private Integer enabled;

    @ApiModelProperty(value = "roleKey(user:普通用户 admin:管理员)")
    private String roleKey;
}
