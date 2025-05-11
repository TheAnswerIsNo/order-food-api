package com.wait.app.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 */

@Getter
@AllArgsConstructor
public enum AttachmentEnum {

    DYNAMIC("动态","dynamic"),

    PET("宠物","pet"),

    GOODS("商品","goods");

    private final String name;

    private final String value;
}
