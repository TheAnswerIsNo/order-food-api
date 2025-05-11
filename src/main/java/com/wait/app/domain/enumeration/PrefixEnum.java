package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 * @description: 前缀枚举
 */
@Getter
@AllArgsConstructor
public enum PrefixEnum {

    ORDER("订单编号前缀", "orderId:"),

    LOCK("锁前缀", "lock:"),

    USER("用户前缀", "userId:");

    private final String name;

    private final String value;
}
