package com.web.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.entity.HouseSource;
import com.web.platform.pojo.query.HouseSourceQuery;
import com.web.platform.pojo.vo.HouseSourceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author YaeMiko
 */
@Repository
public interface HouseSourceMapper extends BaseMapper<HouseSource> {

    /**
     * 分页查询
     * @param page 分页查询参数
     * @param houseSourceQuery 房源表单
     * @return 查询结果
     */
    IPage<HouseSourceVO> sourcePage(Page<HouseSourceVO> page, @Param("houseSourceQuery") HouseSourceQuery houseSourceQuery);
}
