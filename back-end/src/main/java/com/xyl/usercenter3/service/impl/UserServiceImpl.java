package com.xyl.usercenter3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.usercenter3.common.BaseResponse;
import com.xyl.usercenter3.common.ErrorCode;
import com.xyl.usercenter3.common.ResultUtils;
import com.xyl.usercenter3.exception.BusnessException;
import com.xyl.usercenter3.model.domain.User;
import com.xyl.usercenter3.service.UserService;
import com.xyl.usercenter3.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xyl.usercenter3.contant.UserConstant.USER_LOGIN_STATUS;

/**
* @author XYL
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-06-26 22:44:54
 *
 * 用户服务实现类
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper; // 如果注入了userMapper，可以用里面的方法操作数据库，省去这里的this.count那里

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "xyl";

    /**
     * 用户登录态--键，通过这个键(key)找到唯一一个值
     */
    // public static final String USER_LOGIN_STATUS = "userLoginStatus"; // 定义到外边，方便controller调用

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String plantCode) {


        // 1. 校验
        /*
          普通写法：if (userAccount == null || userPassword == null || checkPassword == null)
        *  引入一个库： Commons Lang，可以直接同时判断这几个变量是否为 空、null
        * */
        if(StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) { // 校验是否为空
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "参数为空");
        }
        if(userAccount.length() < 4) { // 校验账户不小于4位
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "账户过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) { // 校验密码不小于8位
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "密码过短");
        }
        if (plantCode.length() > 5) { // 限制长度不超过5
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "星球编号过长");
        }
        // 校验账户名特殊字符
        String vaildPattern = "^[a-zA-Z]\\w{5,17}$";
        Matcher matcher = Pattern.compile(vaildPattern).matcher(userAccount);
        if (!matcher.find()) {
            return -1;
        }
        // 密码与校验想用
        if (!userPassword.equals(checkPassword)) {
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "密码不一致");
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
//        long count = this.count(queryWrapper);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) { // 表示已经有这个用户
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "用户已存在");
        }
        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("plant_code", plantCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
//            return -1;
            throw new BusnessException(ErrorCode.PARAMS_CODE, "星球编号不能重复");
        }
        // 2. 密码加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlantCode((plantCode));
        boolean saveReault = this.save(user);// save方法保存到数据库中
        if (!saveReault) { // 数据库中定义的是Long类型。防止什么装箱拆箱
//            return -1;
            throw new BusnessException(ErrorCode.DATA_NULL_ERROR);
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAllBlank(userAccount, userPassword)) { // 校验是否为空
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        if(userAccount.length() < 4) { // 校验账户不小于4位
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        if (userPassword.length() < 8) { // 校验密码不小于8位
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }
        // 校验账户名特殊字符
        String vaildPattern = "^[a-zA-Z]\\w{5,17}$";
        Matcher matcher = Pattern.compile(vaildPattern).matcher(userAccount);
        if (!matcher.find()) {
//            return null;
            throw new BusnessException(ErrorCode.PARAMS_CODE);
        }

        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询用户是否存在
        /*
        * 去除逻辑删除的用户，mybatis-plus配置，再加上@TableLogic注解
        * */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword); // 数据库中的密码等于加密后的密码
        // 都查到了
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) { // 用户为空
            // 引入了@Slf4j 可以打印日志, 日志用英文可以防止乱码
            log.info("user login failed, userAccount cannot match userPassword");
//            return null;
            throw new BusnessException(ErrorCode.NO_USER);
        }

        // 3. 用户脱敏
        User safetyUser =  safetyUser(user);

        // 4. 记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATUS, user); // setAttribute存入一个键值对
        return safetyUser;
    }

    @Override
    public User safetyUser(User user) {
        if (user == null) {
//            return null;
            throw new BusnessException(ErrorCode.NO_USER);
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setPlantCode(user.getPlantCode());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());

        return safetyUser;
    }

    @Override
    public void userLogoout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
    }
}




