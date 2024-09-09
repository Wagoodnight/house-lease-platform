package com.web.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.common.Response;
import com.web.platform.entity.SysLog;
import com.web.platform.pojo.query.SysLogQuery;
import com.web.platform.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YaeMiko
 */
@Api(tags = "日志模块接口")
@RestController
@RequestMapping("/logs")
public class SysLogController {

    @SuppressWarnings("all")
    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysLogQuery", value = "查询表单", required = true, dataTypeClass = SysLogQuery.class, type = "body")
    })
    @PostMapping("/page")
    public Response<IPage<SysLog>> getSysLogPage(@RequestBody SysLogQuery sysLogQuery) {
        return Response.success(sysLogService.getSysLogPage(sysLogQuery));
    }
}
