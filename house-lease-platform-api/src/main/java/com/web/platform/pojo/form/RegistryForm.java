package com.web.platform.pojo.form;

import lombok.Data;

/**
 * @author zhenxun
 */
@Data
public class RegistryForm {
    private String username;

    private String password;

    private String confirmPassword;

    private String phone;

    private String email;

    private Integer age;

    private Integer sex;

    private String idCard;

    private String userImg;

}
