package com.web.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.common.Response;
import com.web.platform.pojo.dto.HouseRentalRequestDTO;
import com.web.platform.pojo.form.HouseRentalRequestForm;
import com.web.platform.pojo.query.HouseRentalRequestQuery;
import com.web.platform.pojo.vo.HouseRentalRequestVO;
import com.web.platform.service.HouseRentalRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YaeMiko
 */
@Api(tags = "求购模块接口")
@RestController
@RequestMapping("/request")
public class HouseRentalRequestController {

    @SuppressWarnings("all")
    @Autowired
    private HouseRentalRequestService houseRentalRequestService;

    @ApiOperation("新增求购口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "form", value = "求购表单", required = true, dataTypeClass = HouseRentalRequestQuery.class, type = "body")
    })
    @PostMapping("/add")
    public Response<?> addRequest(@RequestBody HouseRentalRequestForm form) {
        houseRentalRequestService.addHouseRentalRequest(form);
        return Response.success();
    }

    @ApiOperation("修改求购接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "form", value = "求购表单", required = true, dataTypeClass = HouseRentalRequestQuery.class, type = "body")
    })
    @PostMapping("/update")
    public Response<?> updateRequest(@RequestBody HouseRentalRequestForm form) {
        HouseRentalRequestDTO houseRentalRequestDTO = new HouseRentalRequestDTO();
        BeanUtils.copyProperties(form,houseRentalRequestDTO);
        houseRentalRequestService.updateHouseRentalRequest(houseRentalRequestDTO);
        return Response.success();
    }

    @ApiOperation("删除求购接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestId", value = "求购ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/delete")
    public Response<?> deleteRequest(@RequestParam Long requestId) {
        houseRentalRequestService.delHouseRentalRequest(requestId);
        return Response.success();
    }

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询表单", required = true, dataTypeClass = HouseRentalRequestQuery.class,type = "body")
    })
    @PostMapping("/page")
    public Response<IPage<HouseRentalRequestVO>> getRequestPage(@RequestBody HouseRentalRequestQuery query) {
        return Response.success(houseRentalRequestService.requestPage(query));
    }

    @ApiOperation("求租详情接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestId", value = "请求ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/details")
    public Response<HouseRentalRequestVO> getRequestDetails(@RequestParam Long requestId){
        return Response.success(houseRentalRequestService.requestDetails(requestId));
    }

    @ApiOperation("我发布的房源查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询表单", required = true, dataTypeClass = HouseRentalRequestQuery.class,type = "body")
    })
    @PostMapping("/myrequestpage")
    public Response<IPage<HouseRentalRequestVO>> getMyRequestPage(@RequestBody HouseRentalRequestQuery query) {
        return Response.success(houseRentalRequestService.myRequestPage(query));
    }

    @ApiOperation("统计记录接口")
    @GetMapping("/count")
    public Response<Long> getSelectCount() {
        return Response.success(houseRentalRequestService.selectCount());
    }

    @ApiOperation("统计我的记录接口")
    @PostMapping("/mycount")
    public Response<Long> getMyCount() {
        return Response.success(houseRentalRequestService.mySelectCount());
    }
}
