package com.web.platform.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */

@Data
public class LoginResultVO {
    private Long userId;

    private String token;

    private String username;

    private String userImg;

}
