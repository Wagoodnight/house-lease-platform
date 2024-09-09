package com.web.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.common.Const;
import com.web.platform.common.ResponseType;
import com.web.platform.exception.ServiceException;
import com.web.platform.entity.HouseRentalRequest;
import com.web.platform.mapper.HouseRentalRequestMapper;
import com.web.platform.mapper.UserMapper;
import com.web.platform.pojo.CurrentLoginUser;
import com.web.platform.pojo.Role;
import com.web.platform.pojo.dto.HouseRentalRequestDTO;
import com.web.platform.pojo.form.HouseRentalRequestForm;
import com.web.platform.pojo.query.HouseRentalRequestQuery;
import com.web.platform.pojo.vo.HouseRentalRequestVO;
import com.web.platform.service.HouseRentalRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


/**
 * @author YaeMiko
 */
@Service
public class HouseRentalRequestServiceImpl implements HouseRentalRequestService {

    @Autowired
    private HouseRentalRequestMapper houseRentalRequestMapper;

    @Autowired
    private UserMapper userMapper;

    private static final Logger log = LoggerFactory.getLogger(HouseRentalRequestServiceImpl.class);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public void addHouseRentalRequest(HouseRentalRequestForm houseRentalRequestForm) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if(userMapper.selectById(loginUser.getUserId()).getPublished() == 0) {
            log.warn("Operation forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        HouseRentalRequest houseRentalRequest = new HouseRentalRequest();
        houseRentalRequest.setUserId(loginUser.getUserId());
        BeanUtils.copyProperties(houseRentalRequestForm,houseRentalRequest);
        if(houseRentalRequestMapper.insert(houseRentalRequest) < 1) {
            log.error("Insert new house rental request ERROR!");
            throw new ServiceException(ResponseType.REQUEST_ADD_ERR);
        }
    }

    @Override
    public void updateHouseRentalRequest(HouseRentalRequestDTO houseRentalRequestDTO) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        HouseRentalRequest houseRentalRequest = new HouseRentalRequest();
        BeanUtils.copyProperties(houseRentalRequestDTO,houseRentalRequest);
        houseRentalRequest.setUserId(loginUser.getUserId());
        if(houseRentalRequestMapper.updateById(houseRentalRequest) < 1) {
            log.error("Update house rental request ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void delHouseRentalRequest(Long requestId) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        log.info("Delete source, source:{},userId:{}", requestId, loginUser.getUserId());
        if(houseRentalRequestMapper.selectById(requestId).getUserId().equals(loginUser.getUserId())) {
            if(houseRentalRequestMapper.deleteById(requestId) < 1) {
                log.error("Delete house rental request ERROR!");
                throw new ServiceException(ResponseType.REQUEST_DEL_ERR);
            }
        } else if (loginUser.getRole().equals(Role.admin)) {
            if(houseRentalRequestMapper.deleteById(requestId) < 1) {
                log.error("Delete house rental request ERROR!");
                throw new ServiceException(ResponseType.REQUEST_DEL_ERR);
            }
        } else {
            log.warn("Operation forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
    }

    @Override
    public IPage<HouseRentalRequestVO> requestPage(HouseRentalRequestQuery houseRentalRequestQuery) {
        log.info("select house rental request by page,query:{}",houseRentalRequestQuery);
        Page<HouseRentalRequestVO> page = new Page<>(houseRentalRequestQuery.getCurrentPage(), houseRentalRequestQuery.getPageSize());
        return houseRentalRequestMapper.requestPage(page, houseRentalRequestQuery);
    }

    @Override
    public HouseRentalRequestVO requestDetails(Long requestId) {
        log.info("View house rental request details. requestId:{}",requestId);
        HouseRentalRequest houseRentalRequest = houseRentalRequestMapper.selectById(requestId);
        HouseRentalRequestVO houseRentalRequestVO = new HouseRentalRequestVO();
        if (houseRentalRequest == null) {
            return null;
        }
        BeanUtils.copyProperties(houseRentalRequest,houseRentalRequestVO);
        return houseRentalRequestVO;
    }

    @Override
    public IPage<HouseRentalRequestVO> myRequestPage(HouseRentalRequestQuery houseRentalRequestQuery) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        houseRentalRequestQuery.setUserId(loginUser.getUserId());
        log.info("select house rental request by page,query:{}",houseRentalRequestQuery);
        Page<HouseRentalRequestVO> page = new Page<>(houseRentalRequestQuery.getCurrentPage(), houseRentalRequestQuery.getPageSize());
        return houseRentalRequestMapper.requestPage(page, houseRentalRequestQuery);
    }

    @Override
    public Long selectCount() {
        return houseRentalRequestMapper.selectCount(null);
    }

    @Override
    public Long mySelectCount() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        QueryWrapper<HouseRentalRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", loginUser.getUserId());
        return houseRentalRequestMapper.selectCount(queryWrapper);
    }
}
