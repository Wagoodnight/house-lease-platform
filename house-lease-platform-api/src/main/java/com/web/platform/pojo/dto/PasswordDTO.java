package com.web.platform.pojo.dto;

import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
public class PasswordDTO {
    private Long username;

    private String oldPwd;

    private String newPwd;
}
