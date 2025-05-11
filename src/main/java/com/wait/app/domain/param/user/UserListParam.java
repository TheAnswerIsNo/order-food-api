package com.wait.app.domain.param.user;

import com.wait.app.utils.page.RequestDTOWithPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 * Time: 2025/4/21 13:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListParam extends RequestDTOWithPage {

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
