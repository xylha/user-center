package com.xyl.usercenter3.service;

import com.xyl.usercenter3.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author XYL
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-06-26 22:44:54
 *
 * 用户服务
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param plantCode 星球编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String plantCode);

    /**
     *  用户登录
     * @param userAccount 账户
     * @param userPassword 密码
     * @param request 后端基于session取出该用户的信息
     * @return 脱敏后用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user 用户类
     * @return 用户信息
     */
    User safetyUser(User user);

    /**
     * 用户注销
     * @param request 用户登录态
     * @return 是否注销成功
     */
    void userLogoout(HttpServletRequest request);
}
