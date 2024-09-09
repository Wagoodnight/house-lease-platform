package com.web.platform.pojo.form;

import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class PasswordForm {

    private String username;

    private String oldPwd;

    private String newPwd;
}
