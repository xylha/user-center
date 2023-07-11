package com.xyl.usercenter3.common;

/**
 * 处理结果工具类
 * @author XYL
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok", "");
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDesc() );
    }

    /**
     * 失败
     * @param
     * @return
     */
    public static BaseResponse error(int code, String msg, String des) {
        return new BaseResponse<>(code, null, msg, des );
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String msg, String des) {
        return new BaseResponse<>(errorCode.getCode(), null, msg, des );
    }
}
