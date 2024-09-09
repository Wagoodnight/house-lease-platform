package com.web.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.platform.common.Response;
import com.web.platform.pojo.dto.HouseSourceDTO;
import com.web.platform.pojo.form.HouseSourceForm;
import com.web.platform.pojo.query.HouseSourceQuery;
import com.web.platform.pojo.vo.HouseSourceVO;
import com.web.platform.service.HouseSourceService;
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
@Api(tags = "房源模块接口")
@RestController
@RequestMapping("/source")
public class HouseSourceController {

    @SuppressWarnings("all")
    @Autowired
    private HouseSourceService houseSourceService;

    @ApiOperation("添加房源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseSourceForm", value = "房源表单", required = true, dataTypeClass = HouseSourceForm.class, type = "body")
    })
    @PostMapping("/add")
    public Response<?> addSource(@RequestBody HouseSourceForm houseSourceForm) {
        houseSourceService.addSource(houseSourceForm);
        return Response.success();
    }

    @ApiOperation("更新房源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseSourceForm", value = "房源表单", required = true, dataTypeClass = HouseSourceQuery.class, type = "body")
    })
    @PostMapping("/update")
    public Response<?> updateSource(@RequestBody HouseSourceForm houseSourceForm) {
        HouseSourceDTO houseSourceDTO = new HouseSourceDTO();
        BeanUtils.copyProperties(houseSourceForm, houseSourceDTO);
        houseSourceService.updateSource(houseSourceDTO);
        return Response.success();
    }

    @ApiOperation("删除房源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId", value = "房源ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/delete")
    public Response<?> deleteSource(@RequestParam Long houseId) {
        houseSourceService.delSource(houseId);
        return Response.success();
    }

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseSourceQuery", value = "查询表单", required = true, dataTypeClass = HouseSourceQuery.class, type = "body")
    })
    @PostMapping("/page")
    public Response<IPage<HouseSourceVO>> getSourcePage(@RequestBody HouseSourceQuery houseSourceQuery) {
        return Response.success(houseSourceService.sourcePage(houseSourceQuery));
    }

    @ApiOperation("房源详情接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseId", value = "房源ID", required = true, dataTypeClass = Long.class, example = "0", defaultValue = "0")
    })
    @PostMapping("/details")
    public Response<HouseSourceVO> getSourceDetails(@RequestParam Long houseId) {
        return Response.success(houseSourceService.sourceDetails(houseId));
    }

    @ApiOperation("我发布的房源查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseSourceQuery", value = "查询表单", required = true, dataTypeClass = HouseSourceQuery.class, type = "body")
    })
    @PostMapping("/mySourcePage")
    public Response<IPage<HouseSourceVO>> getMySourcePage(@RequestBody HouseSourceQuery houseSourceQuery) {
        return Response.success(houseSourceService.mySourcePage(houseSourceQuery));
    }

    @ApiOperation("统计记录接口")
    @GetMapping("/count")
    public Response<Long> getSelectCount() {
        return Response.success(houseSourceService.selectCount());
    }

    @ApiOperation("统计我的记录接口")
    @PostMapping("/mycount")
    public Response<Long> getMyCount() {
        return Response.success(houseSourceService.mySelectCount());
    }
}
