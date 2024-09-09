package com.web.platform.pojo.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class LoginForm {
    private String username;

    private String password;
}
