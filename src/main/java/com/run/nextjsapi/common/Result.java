package com.run.nextjsapi.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@Data
public class Result {

    private Boolean isSuccess;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static Result ok() {
        Result result = new Result();
        result.setIsSuccess(ResultCode.SUCCESS.getIsSuccess());
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setIsSuccess(ResultCode.UNKNOWN_ERROR.getIsSuccess());
        result.setCode(ResultCode.UNKNOWN_ERROR.getCode());
        result.setMessage(ResultCode.UNKNOWN_ERROR.getMessage());
        return result;
    }

    public static Result build(ResultCode resultCode) {
        Result result = new Result();
        result.setIsSuccess(resultCode.getIsSuccess());
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    public static Result build(ResultCode resultCode, Map<String, Object> data) {
        Result result = new Result();
        result.setIsSuccess(resultCode.getIsSuccess());
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setData(data);
        return result;
    }

    public Result success(Boolean isSuccess) {
        this.setIsSuccess(isSuccess);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result appendData(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    public Result appendData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
