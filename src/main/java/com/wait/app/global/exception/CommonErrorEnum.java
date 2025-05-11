package com.wait.app.global.exception;

import cn.dev33.satoken.error.SaErrorCode;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;

/**
 * @author 天
 *
 * @description: 通用异常码
 */
@AllArgsConstructor
public enum CommonErrorEnum implements ErrorEnum{

    SYSTEM_ERROR(SaResult.CODE_ERROR,"系统出小差了，请稍后重试"),
    BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST,"唯一值异常,请重新输入参数"),
    PERMISSION_ERROR(SaErrorCode.CODE_11051,"权限异常");

    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
