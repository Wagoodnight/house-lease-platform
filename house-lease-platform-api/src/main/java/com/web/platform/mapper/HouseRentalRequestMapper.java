package com.web.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.entity.HouseRentalRequest;
import com.web.platform.pojo.query.HouseRentalRequestQuery;
import com.web.platform.pojo.vo.HouseRentalRequestVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author YaeMiko
 */
@Repository
public interface HouseRentalRequestMapper extends BaseMapper<HouseRentalRequest> {
    /**
     * 分页查询
     * @param page 分页表单
     * @param houseRentalRequestQuery 查询表单
     * @return 查询结果
     */
    IPage<HouseRentalRequestVO> requestPage(Page<HouseRentalRequestVO> page, @Param("houseRentalRequestQuery") HouseRentalRequestQuery houseRentalRequestQuery);

}
