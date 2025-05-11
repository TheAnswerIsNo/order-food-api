package com.wait.app.domain.param.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 * Time: 2025/1/3 9:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebLoginParam {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;
}
