package com.web.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.entity.SysLog;
import com.web.platform.pojo.query.SysLogQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author YaeMiko
 */
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 分页查询
     * @param page 分页参数
     * @param sysLogQuery 查询表单
     * @return 查询结果
     */
    IPage<SysLog> selectByPage(Page<SysLog> page, @Param("sysLogQuery")SysLogQuery sysLogQuery);
}
