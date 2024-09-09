package com.web.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.common.Const;
import com.web.platform.common.ResponseType;
import com.web.platform.entity.SysLog;
import com.web.platform.exception.ServiceException;
import com.web.platform.entity.User;
import com.web.platform.handler.RoleTypeHandler;
import com.web.platform.mapper.SysLogMapper;
import com.web.platform.mapper.UserMapper;
import com.web.platform.pojo.CurrentLoginUser;
import com.web.platform.pojo.Role;
import com.web.platform.pojo.dto.PasswordDTO;
import com.web.platform.pojo.dto.UserDTO;
import com.web.platform.pojo.form.LoginForm;
import com.web.platform.pojo.form.RegistryForm;
import com.web.platform.pojo.query.UserQuery;
import com.web.platform.pojo.vo.AutoLoginVO;
import com.web.platform.pojo.vo.LoginResultVO;
import com.web.platform.pojo.vo.UserVO;
import com.web.platform.service.UserService;
import com.web.platform.util.EncryptUtil;
import com.web.platform.util.TokenUtil;
import org.apache.ibatis.annotations.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    @SuppressWarnings("all")
    @Autowired(required = false)
    private HttpServletRequest request;

    @SuppressWarnings("all")
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    @Result(column = "role", property = "role", typeHandler = RoleTypeHandler.class)
    public LoginResultVO login(LoginForm loginForm) {
        log.info("User login:{}", loginForm.getUsername());
        loginForm.setPassword(EncryptUtil.encrypt(loginForm.getPassword()));
        UserVO userVO =userMapper.userLogin(loginForm);
        if(userVO == null) {
            log.warn("User login FAILED!");
            throw new ServiceException(ResponseType.LOGIN_FAILED);
        } else {
            CurrentLoginUser loginUser = new CurrentLoginUser();
            loginUser.setUserId(userVO.getUserId());
            loginUser.setRole(userVO.getRole());
            String token = TokenUtil.createLoginToken(loginUser);
            LoginResultVO loginResultVO = new LoginResultVO();
            BeanUtils.copyProperties(userVO, loginResultVO);
            loginResultVO.setToken(token);
            User user = new User();
            user.setUserId(userVO.getUserId());
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.updateById(user);
            SysLog sysLog = new SysLog();
            sysLog.setOperateName("loginUser");
            sysLog.setParameter("[" + JSON.toJSONString(loginForm) + "]");
            sysLog.setUserId(userVO.getUserId());
            sysLog.setResult("200");
            sysLogMapper.insert(sysLog);
            return loginResultVO;
        }
    }

    @Override
    public void registry(RegistryForm registryForm) {
        log.info("User Registry:{}",registryForm.getUsername());
        registryForm.setPassword(EncryptUtil.encrypt(registryForm.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(registryForm,user);
        if(userMapper.insert(user) < 1) {
            log.error("Registry user ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void updatePassword(PasswordDTO passwordDTO) {
        log.info("User updating password:{}",passwordDTO.getUsername());
        passwordDTO.setOldPwd(EncryptUtil.encrypt(passwordDTO.getOldPwd()));
        passwordDTO.setNewPwd(EncryptUtil.encrypt(passwordDTO.getNewPwd()));
        if(userMapper.updatePassword(passwordDTO) < 1) {
            log.warn("User update password FAILED!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        log.info("delete user, role:{},userId:{}", loginUser.getRole(), loginUser.getUserId());
        if(!loginUser.getRole().equals(Role.admin)) {
            log.warn("Insufficient permissions for userId: {} trying to delete user for userId: {}", loginUser.getUserId(), userId);
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        int result = userMapper.deleteById(userId);
        if(result < 1) {
            log.error("Delete user ERROR, userId:{}", userId);
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public UserVO space() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        return userMapper.space(loginUser.getUserId());
    }

    @Override
    public UserVO detail(Long userId) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if(!loginUser.getRole().equals(Role.admin)) {
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        } else {
            User user = userMapper.selectById(userId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }
    }

    @Override
    public IPage<UserVO> getUserPage(UserQuery userQuery) {
        log.info("SELECT user by page, query :{}",userQuery);
        Page<UserVO> page = new Page<>(userQuery.getCurrentPage(), userQuery.getPageSize());
        return userMapper.registed(page,userQuery);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if (loginUser.getRole().equals(Role.admin)) {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            if(userMapper.updateById(user) < 1) {
                log.error("Update user ERROR!");
                throw new ServiceException(ResponseType.SERVER_ERR);
            }
        } else {
            log.warn("Operation Forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
    }

    @Override
    public AutoLoginVO testToken() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        log.info("Auto Login, userId:{}",loginUser.getUserId());
        log.info("User Role :{}",loginUser.getRole());
        AutoLoginVO autoLoginVO = new AutoLoginVO();
        autoLoginVO.setRole(loginUser.getRole().toString());
        User user = userMapper.selectById(loginUser.getUserId());
        autoLoginVO.setPublished(user.getPublished());
        autoLoginVO.setRentalRequest(user.getRentalRequest());
        return autoLoginVO;
    }

    @Override
    public Long selectCount() {
        return userMapper.selectCount(null);
    }
}
