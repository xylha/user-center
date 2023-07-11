package com.xyl.usercenter3.exception;

import com.xyl.usercenter3.common.BaseResponse;
import com.xyl.usercenter3.common.ErrorCode;
import com.xyl.usercenter3.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusnessException.class)
    public BaseResponse businessExceptionHandler(BusnessException e) {
        log.error("businessException" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDesc());
    }

    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeExpection", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
