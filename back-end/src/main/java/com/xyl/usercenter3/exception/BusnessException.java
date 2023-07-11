package com.xyl.usercenter3.exception;

import com.xyl.usercenter3.common.ErrorCode;

/**
 * 自定义 异常类
 * @author XYL
 */
public class BusnessException extends RuntimeException {
    private final int code;
    private final String desc;

    public BusnessException(String message, int code, String desc) {
        super(message);
        this.code = code;
        this.desc = desc;
    }
    public BusnessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
    }

    public BusnessException(ErrorCode errorCode, String desc) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
