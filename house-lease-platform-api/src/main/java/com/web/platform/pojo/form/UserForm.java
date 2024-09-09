package com.web.platform.pojo.form;

import com.web.platform.pojo.Role;
import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class UserForm {

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
}
