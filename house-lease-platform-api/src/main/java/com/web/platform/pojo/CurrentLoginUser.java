package com.web.platform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLoginUser {

    private Long userId;

    private Role role;
}
