package com.web.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.common.Const;
import com.web.platform.common.ResponseType;
import com.web.platform.entity.SysLog;
import com.web.platform.exception.ServiceException;
import com.web.platform.mapper.SysLogMapper;
import com.web.platform.pojo.CurrentLoginUser;
import com.web.platform.pojo.Role;
import com.web.platform.pojo.query.SysLogQuery;
import com.web.platform.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YaeMiko
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @SuppressWarnings("all")
    @Autowired
    private SysLogMapper sysLogMapper;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @SuppressWarnings("all")
    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public IPage<SysLog> getSysLogPage(SysLogQuery sysLogQuery) {
        log.info("SELECT syslog by page, query: " + sysLogQuery);
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if(!loginUser.getRole().equals(Role.admin)) {
            log.warn("Operation Forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        Page<SysLog> page = new Page<>(sysLogQuery.getCurrentPage(), sysLogQuery.getPageSize());
        return sysLogMapper.selectByPage(page, sysLogQuery);
    }
}
