package com.run.nextjsapi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(true, 2000, "success"),
    UNKNOWN_ERROR(false, 4000, "unknown error"),
    PARAM_ERROR(false, 4001, "param error"),
    DATABASE_ERROR(false, 4002, "database error");


    private Boolean isSuccess;
    private Integer code;
    private String message;

}
