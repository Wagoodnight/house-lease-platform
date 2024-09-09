package com.web.platform.pojo.vo;

import com.web.platform.pojo.Role;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Data
public class UserVO {
    private Long userId;

    private String username;

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
