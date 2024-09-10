package com.web.platform.pojo.query;

import com.web.platform.pojo.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author YaeMiko
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends Page{
    private Long userId;

    private String username;

    private String phone;

    private String email;

    private Integer maxAge;

    private Integer minAge;

    private Integer sex;

    private String idCard;

    private Role role;

    private Integer rentalRequest;

    private Integer published;

    private LocalDateTime startTime;

    private  LocalDateTime endTime;
}
