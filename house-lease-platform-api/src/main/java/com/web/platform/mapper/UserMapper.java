package com.web.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.entity.User;
import com.web.platform.pojo.dto.PasswordDTO;
import com.web.platform.pojo.form.*;
import com.web.platform.pojo.query.UserQuery;
import com.web.platform.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author YaeMiko
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 登陆接口
     * @param loginForm 登陆表单
     * @return 登陆状态
     */
    UserVO userLogin(@Param("loginForm") LoginForm loginForm);

    /**
     * 修改密码
     * @param passwordDTO 修改密码表单
     * @return 改密状态
     */
    int updatePassword(@Param("passwordDTO") PasswordDTO passwordDTO);

    /**
     * 我的信息
     * @param userId 用户Id
     * @return 个人信息
     */
    UserVO space(@Param("userId") Long userId);

    /**
     * 分页查询
     * @param page 查询参数
     * @param userQuery 用户表单
     * @return 查询结果
     */
    IPage<UserVO> registed(Page<UserVO> page, @Param("userQuery")UserQuery userQuery);
}
