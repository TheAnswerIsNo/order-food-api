package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 */

@Getter
@AllArgsConstructor
public enum WeChatURLEnum {
    //微信登录获取openid接口,GET
    WECHAT_LOGIN_URL("https://api.weixin.qq.com/sns/jscode2session"),

    //微信获取access_token，GET
    WECHAT_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token"),

    WECHAT_PHONE("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="),

    // 获取用户信息
    WECHAT_USER_INFO("https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID");

    private final String name;
}
