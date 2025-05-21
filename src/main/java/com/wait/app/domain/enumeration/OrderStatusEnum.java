package com.wait.app.domain.enumeration;

import lombok.Getter;

/**
 * @author 天
 *
 * @description: 订单状态枚举
 */
@Getter
public enum OrderStatusEnum {

    WFK("未付款",0),

    ZZZ("制作中",1),

    YWC("已完成",2),

    DDQX("订单取消",3),
    ;

    private final String name;

    private final Integer value;

    OrderStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
