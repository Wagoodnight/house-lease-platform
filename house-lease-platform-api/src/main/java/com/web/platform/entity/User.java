package com.web.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.web.platform.pojo.Role;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Data
@TableName("t_user")
public class User {
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer age;

    private Integer sex;

    private String idCard;

    private Role role;

    private Integer rentalRequest;

    private Integer published;

    private String userImg;

    private LocalDateTime lastLoginTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
