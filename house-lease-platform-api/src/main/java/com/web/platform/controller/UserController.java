package com.web.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.common.Response;
import com.web.platform.pojo.dto.PasswordDTO;
import com.web.platform.pojo.dto.UserDTO;
import com.web.platform.pojo.form.LoginForm;
import com.web.platform.pojo.form.PasswordForm;
import com.web.platform.pojo.form.RegistryForm;
import com.web.platform.pojo.form.UserForm;
import com.web.platform.pojo.query.UserQuery;
import com.web.platform.pojo.vo.AutoLoginVO;
import com.web.platform.pojo.vo.LoginResultVO;
import com.web.platform.pojo.vo.UserVO;
import com.web.platform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YaeMiko
 */
@Api(tags = "用户模块接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    private UserService userService;

    @ApiOperation("登陆接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "loginForm", value = "登陆表单", required = true, dataTypeClass = LoginForm.class, type = "body")
    })
    @PostMapping("/login")
    public Response<LoginResultVO> loginUser(@RequestBody LoginForm loginForm) {
        LoginResultVO loginResultVO =userService.login(loginForm);
        return Response.success(loginResultVO);
    }

    @ApiOperation("注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registryForm", value = "注册表单", required = true, dataTypeClass = RegistryForm.class, type = "body")
    })
    @PostMapping("/registry")
    public Response<?> registryUser(@RequestBody RegistryForm registryForm) {
        userService.registry(registryForm);
        return Response.success();
    }

    @ApiOperation("改密接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passwordForm", value = "改密表单", required = true, dataTypeClass = PasswordForm.class, type = "body")
    })
    @PostMapping("/update-pwd")
    public Response<?> updatePwd(@RequestBody PasswordForm passwordForm) {
        PasswordDTO passwordDTO = new PasswordDTO();
        BeanUtils.copyProperties(passwordForm,passwordDTO);
        userService.updatePassword(passwordDTO);
        return Response.success();
    }

    @ApiOperation("个人空间接口")
    @GetMapping("/space")
    public Response<UserVO> getMySpace() {
        return Response.success(userService.space());
    }

    @ApiOperation("更新用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userForm", value = "用户表单", required = true, dataTypeClass = UserForm.class, type = "body")
    })
        @PostMapping("/update-user")
    public Response<?> updateUser(@RequestBody UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userForm, userDTO);
        userService.updateUser(userDTO);
        return Response.success();
    }

    @ApiOperation("删除用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/del-user")
    public Response<?> delUser(Long userId) {
        userService.deleteUser(userId);
        return Response.success();
    }

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userQuery", value = "查询表单", required = true, dataTypeClass = UserQuery.class, type = "body")
    })
    @PostMapping("/page")
    public Response<IPage<UserVO>> getUserPage(@RequestBody UserQuery userQuery) {
        return Response.success(userService.getUserPage(userQuery));
    }

    @ApiOperation("Token测试接口")
    @GetMapping("/testToken")
    public Response<AutoLoginVO> testToken() {
        return Response.success(userService.testToken());
    }

    @ApiOperation("统计记录接口")
    @GetMapping("/count")
    public Response<Long> getSelectCount() {
        return Response.success(userService.selectCount());
    }

    @ApiOperation("用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/detail")
    public Response<UserVO> getUserDetail(Long userId) {
        return Response.success(userService.detail(userId));
    }
}
