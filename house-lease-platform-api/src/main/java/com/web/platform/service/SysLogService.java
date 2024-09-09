package com.web.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.entity.SysLog;
import com.web.platform.pojo.query.SysLogQuery;

/**
 * @author YaeMiko
 */
public interface SysLogService {

    /**
     * 分页查询
     * @param sysLogQuery 查询表单
     * @return 查询结果
     */
    IPage<SysLog> getSysLogPage(SysLogQuery sysLogQuery);
}
