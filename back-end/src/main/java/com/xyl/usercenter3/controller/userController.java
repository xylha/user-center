package com.xyl.usercenter3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyl.usercenter3.common.BaseResponse;
import com.xyl.usercenter3.common.ErrorCode;
import com.xyl.usercenter3.common.ResultUtils;
import com.xyl.usercenter3.exception.BusnessException;
import com.xyl.usercenter3.model.domain.User;
import com.xyl.usercenter3.model.domain.request.UserLoginRequest;
import com.xyl.usercenter3.model.domain.request.UserRegisterRequest;
import com.xyl.usercenter3.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xyl.usercenter3.contant.UserConstant.ADMIN_ROLE;
import static com.xyl.usercenter3.contant.UserConstant.USER_LOGIN_STATUS;

@RestController // 使返回值为JSON类型
@RequestMapping("/user")
public class userController {

    @Resource
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return null;
//            return ResultUtils.error(ErrorCode.PARAMS_CODE);
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        String userPassword = userRegisterRequest.getUserPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String plantCode = userRegisterRequest.getPlantCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword,checkPassword)) {
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword, plantCode);
//        return new BaseResponse<>(0, l, "ok");
        return ResultUtils.success(l);
    }

    // 用户登录
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        if (userLoginRequest == null ) {
//            return null;
//            return ResultUtils.error(ErrorCode.PARAMS_CODE);
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        String userPassword = userLoginRequest.getUserPassword();
        String userAccount = userLoginRequest.getUserAccount();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
//            return null;
//            return ResultUtils.error(ErrorCode.PARAMS_CODE);
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        User user = userService.doLogin(userAccount, userPassword, httpServletRequest);
        return ResultUtils.success(user);
    }
    // 用户登录
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
//            return null;
//            return ResultUtils.error(ErrorCode.NOT_LOGIN);
            throw new BusnessException(ErrorCode.NOT_LOGIN);
        }
        userService.userLogoout(request);
        return ResultUtils.success(1);
    }


    // 获取登录的用户信息
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object  userObj= request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        // 校验用户信息是否合法
        User user = userService.getById(currentUser.getId());
        User user1 = userService.safetyUser(user);
        return ResultUtils.success(user1);
    }

    // 查询用户
    @GetMapping("/search")
    public BaseResponse<List<User>> queryUser(String userName, HttpServletRequest request) {

        /*
        * 这里和下面代码相同，可以提取出来
        * */
        /*Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        if (user == null || user.getRole() != ADMIN_ROLE) {
            return new ArrayList<>();
        }*/
        if (!isAdmin(request)) {
//            return  new ArrayList<>();
                throw new BusnessException(ErrorCode.NOT_LOGIN, "未登录");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }

        // return userService.list(queryWrapper); // 没有处理password
        List<User> userList = userService.list(queryWrapper);
        List<User> collect = userList.stream().map(user -> userService.safetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }

    // 用户删除
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        /*Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        if (user == null || user.getRole() != ADMIN_ROLE) {
            return false;
        }*/
        if (!isAdmin(request)) {
//            return null;
            throw new BusnessException(ErrorCode.NO_AUTH);
        }

        if (id <= 0) {
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        return user != null || user.getUserRole() == ADMIN_ROLE;
    }

}
