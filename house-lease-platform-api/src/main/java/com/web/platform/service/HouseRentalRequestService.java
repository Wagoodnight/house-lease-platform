package com.web.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.pojo.dto.HouseRentalRequestDTO;
import com.web.platform.pojo.form.HouseRentalRequestForm;
import com.web.platform.pojo.query.HouseRentalRequestQuery;
import com.web.platform.pojo.vo.HouseRentalRequestVO;

/**
 * @author YaeMiko
 */
public interface HouseRentalRequestService {
    /**
     * 新增求购
     * @param houseRentalRequestForm 求购表单
     */
    void addHouseRentalRequest(HouseRentalRequestForm houseRentalRequestForm);

    /**
     * 修改求购
     * @param houseRentalRequestDTO 求购表单
     */
    void updateHouseRentalRequest(HouseRentalRequestDTO houseRentalRequestDTO);

    /**
     * 删除求购
     * @param requestId 求购ID
     */
    void delHouseRentalRequest(Long requestId);

    /**
     * 分页查询
     * @param houseRentalRequestQuery 查询表单
     * @return 查询结果
     */
    IPage<HouseRentalRequestVO> requestPage(HouseRentalRequestQuery houseRentalRequestQuery);

    /**
     * 求购详情
     * @param requestId 求购ID
     * @return 查询结果
     */
    HouseRentalRequestVO requestDetails(Long requestId);

    /**
     * 我发布的求购查询
     * @param houseRentalRequestQuery 查询表单
     * @return 查询结果
     */
    IPage<HouseRentalRequestVO> myRequestPage(HouseRentalRequestQuery houseRentalRequestQuery);

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
