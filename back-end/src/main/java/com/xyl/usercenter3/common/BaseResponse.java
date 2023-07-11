package com.xyl.usercenter3.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 * @author XYL
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private String desc;

    public BaseResponse(int code, T data, String message, String desc) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.desc = desc;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDesc());
    }
}
