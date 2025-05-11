package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 */

@Getter
@AllArgsConstructor
public enum EnaledEnum {

    ENABLE("启用",1),
    DISABLE("禁用",0);

    private final String name;

    private final Integer value;
}
