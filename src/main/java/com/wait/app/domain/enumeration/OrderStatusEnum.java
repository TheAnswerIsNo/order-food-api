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

    SHZ("送货中",1),

    YSD("已送达",2),

    YWC("已完成",3),

    DDQX("订单取消",4),
    ;

    private final String name;

    private final Integer value;

    OrderStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
