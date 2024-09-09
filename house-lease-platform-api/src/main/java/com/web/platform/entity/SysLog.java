package com.web.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author YaeMiko
 */
@Data
@TableName("t_sys_log")
public class SysLog {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    private Long userId;

    private String operateName;

    private String parameter;

    private String result;
}
