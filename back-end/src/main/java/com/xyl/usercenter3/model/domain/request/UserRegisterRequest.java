package com.xyl.usercenter3.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author XYL
 */
@Data
public class UserRegisterRequest implements Serializable {

    // 生成序列化UID
    private static final long serialVersionUID = -1056300581808451063L;
    //定义前端需要传的参数
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String plantCode;
}
