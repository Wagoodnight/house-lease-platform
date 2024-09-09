package com.web.platform.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YaeMiko
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogQuery extends Page{
    private Long userId;

    private String operateName;

    private String result;
}
