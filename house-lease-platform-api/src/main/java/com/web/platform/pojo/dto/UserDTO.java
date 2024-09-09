package com.web.platform.pojo.dto;

import com.web.platform.pojo.Role;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Data
public class UserDTO {
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
}
