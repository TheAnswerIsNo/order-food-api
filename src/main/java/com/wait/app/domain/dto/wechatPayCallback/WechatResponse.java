package com.wait.app.domain.dto.wechatPayCallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author 天
 * Time: 2023/11/8 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatResponse {

    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";

    private String code;

    private String message;
}
