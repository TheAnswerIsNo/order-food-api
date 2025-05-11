package com.wait.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.wait.app.domain.enumeration.PrefixEnum;

/**
 * @author 天
 * Time: 2024/9/11 23:38
 */
public abstract class BaseController {

    protected String getUserId(){

        String loginId = StpUtil.getLoginIdAsString();
        return loginId.split(PrefixEnum.USER.getValue())[1];
    }

}
