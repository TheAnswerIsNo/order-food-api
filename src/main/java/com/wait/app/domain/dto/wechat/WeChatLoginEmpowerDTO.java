package com.wait.app.domain.dto.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatLoginEmpowerDTO {

    //会话密钥
    @JsonProperty("session_key")
    private String sessionKey;

    //用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台账号下会返回.
    private String unionid;

    //错误信息
    private String errmsg;

    //用户唯一标识
    private String openid;

    //错误码
    private Integer errcode;

}
