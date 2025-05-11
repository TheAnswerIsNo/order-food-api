package com.wait.app.domain.dto.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/9/7 12:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatUserInfoDTO {

    private String openid;

    private String nickname;

    private Integer sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private List<String> privilege;

    private String unionid;

}
