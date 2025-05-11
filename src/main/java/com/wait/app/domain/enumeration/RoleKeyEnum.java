package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author 天
 * Time: 2024/9/15 11:00
 */
@Getter
@AllArgsConstructor
public enum RoleKeyEnum {

    USER("普通用户","user"),

    ADMIN("管理员","admin");

    private final String name;

    private final String roleKey;
}
