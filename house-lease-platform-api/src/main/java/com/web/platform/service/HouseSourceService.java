package com.web.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.pojo.dto.HouseSourceDTO;
import com.web.platform.pojo.form.HouseSourceForm;
import com.web.platform.pojo.query.HouseSourceQuery;
import com.web.platform.pojo.vo.HouseSourceVO;

/**
 * @author YaeMiko
 */
public interface HouseSourceService {
    /**
     * 添加房源
     * @param houseSourceForm 房源表单
     */
    void addSource(HouseSourceForm houseSourceForm);

    /**
     * 修改房源
     * @param houseSourceDTO 房源表单
     */
    void updateSource(HouseSourceDTO houseSourceDTO);

    /**
     * 删除房源
     * @param houseId 房源ID
     */
    void delSource(Long houseId);

    /**
     * 分页查询
     * @param houseSourceQuery 查询表单
     * @return 查询结果
     */
    IPage<HouseSourceVO> sourcePage(HouseSourceQuery houseSourceQuery);

    /**
     * 房源详情
     * @param houseId 房源ID
     * @return 查询结果
     */
    HouseSourceVO sourceDetails(Long houseId);

    /**
     * 我发布的房源查询
     * @param houseSourceQuery 查询表单
     * @return 查询结果
     */
    IPage<HouseSourceVO> mySourcePage(HouseSourceQuery houseSourceQuery);

    /**
     * 查询总记录数
     * @return 查询结果
     */
    Long selectCount();

    /**
     * 查询我的记录数
     * @return 查询结果
     */
    Long mySelectCount();
}
