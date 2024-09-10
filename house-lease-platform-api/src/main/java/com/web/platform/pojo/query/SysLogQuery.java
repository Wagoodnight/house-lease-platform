package com.web.platform.pojo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author YaeMiko
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogQuery extends Page{
    private Long userId;

    private String operateName;

    private String result;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
