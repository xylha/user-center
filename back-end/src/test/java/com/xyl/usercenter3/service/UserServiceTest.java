package com.xyl.usercenter3.service;

import com.xyl.usercenter3.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 * 自动测试
 * */
@SpringBootTest
class UserServiceTest {

    @Resource
    private com.xyl.usercenter3.service.UserService userService;
    // 引入jupiter的Test API
    @Test
    public void testAddUser() {

        User user = new User();
        user.setUsername("testXYL");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "loginXYL";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String plantCode = "00001";
        userService.userRegister(userAccount, userPassword,checkPassword, plantCode);
        /*long result = userService.userRegister(userAccount, userPassword, checkPassword);
        // 判断密码为空
        Assertions.assertEquals(-1, result); // 失败会返回-1，所以预期的值是-1，实际的值是result
        // 判断账户小于 4 位
        userAccount = "xyl";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 校验密码不小于8位
        userAccount = "hhxyl";
        userPassword="123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 校验账户正则

        // 校验两个密码一样
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        // 校验是否成功
        userAccount = "accountXYL";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);*/
    }
}