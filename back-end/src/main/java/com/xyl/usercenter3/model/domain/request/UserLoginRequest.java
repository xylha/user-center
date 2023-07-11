package com.xyl.usercenter3.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 272558689209131169L;

    private String userAccount;
    private String userPassword;
}
