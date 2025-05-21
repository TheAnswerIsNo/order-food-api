package com.wait.app.domain.param.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 * Time: 2025/5/17 21:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveParam {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;
}
