package com.web.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.pojo.dto.PasswordDTO;
import com.web.platform.pojo.dto.UserDTO;
import com.web.platform.pojo.form.LoginForm;
import com.web.platform.pojo.form.RegistryForm;
import com.web.platform.pojo.query.UserQuery;
import com.web.platform.pojo.vo.AutoLoginVO;
import com.web.platform.pojo.vo.LoginResultVO;
import com.web.platform.pojo.vo.UserVO;

/**
 * @author YaeMiko
 */
public interface UserService {

    /**
     * 登陆
     * @param loginForm 登陆表单
     * @return 登陆结果
     */
    LoginResultVO login(LoginForm loginForm);

    /**
     * 注册
     * @param registryForm 注册表单
     */
    void registry(RegistryForm registryForm);

    /**
     * 修改密码
     * @param passwordDTO 密码表单
     */
    void updatePassword(PasswordDTO passwordDTO);

    /**
     * 删除用户
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 个人空间
     * @return 个人信息
     */
    UserVO space();

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return 查询结果
     */
    UserVO detail(Long userId);

    /**
     * 查询已注册用户
     * @param userQuery 查询表单
     * @return 查询结果
     */
    IPage<UserVO> getUserPage(UserQuery userQuery);

    /**
     * 更新用户信息
     * @param userDTO 用户表单
     */
    void updateUser(UserDTO userDTO);

    /**
     * 自动登录接口
     * @return 权限
     */
    AutoLoginVO testToken();

    /**
     * 查询总记录数
     * @return 查询结果
     */
    Long selectCount();
}
