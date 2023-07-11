package com.xyl.usercenter3.common;

/**
 * 错误码
 * @author XYL
 */
public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAMS_CODE(40000, "请求参数错误", ""),
    DATA_NULL_ERROR(40001, "数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NO_USER(41000, "未查询到用户", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");
    private final int code;
    private final String message;
    private final String desc;

    ErrorCode(int code, String message, String desc) {
        this.code = code;
        this.message = message;
        this.desc = desc;
    }

    // 生成get方法，枚举不允许Set
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDesc() {
        return desc;
    }
}
