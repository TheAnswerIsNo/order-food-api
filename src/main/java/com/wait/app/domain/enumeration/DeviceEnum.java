package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 *
 * @description: 设备登录枚举
 */
@Getter
@AllArgsConstructor
public enum DeviceEnum {

    WEB("web"),

    APPLET("applet");

    private final String value;

}
