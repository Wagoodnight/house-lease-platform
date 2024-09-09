package com.web.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.common.Const;
import com.web.platform.common.ResponseType;
import com.web.platform.exception.ServiceException;
import com.web.platform.entity.HouseSource;
import com.web.platform.mapper.HouseSourceMapper;
import com.web.platform.mapper.UserMapper;
import com.web.platform.pojo.CurrentLoginUser;
import com.web.platform.pojo.Role;
import com.web.platform.pojo.dto.HouseSourceDTO;
import com.web.platform.pojo.form.HouseSourceForm;
import com.web.platform.pojo.query.HouseSourceQuery;
import com.web.platform.pojo.vo.HouseSourceVO;
import com.web.platform.service.HouseSourceService;
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
public class HouseSourceServiceImpl implements HouseSourceService {

    private static final Logger log = LoggerFactory.getLogger(HouseSourceServiceImpl.class);
    @Autowired
    private HouseSourceMapper houseSourceMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Override
    public void addSource(HouseSourceForm houseSourceForm) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        if(userMapper.selectById(loginUser.getUserId()).getPublished() == 0) {
            log.warn("Operation forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
        HouseSource houseSource = new HouseSource();
        houseSource.setUserId(loginUser.getUserId());
        BeanUtils.copyProperties(houseSourceForm,houseSource);
        if(houseSourceMapper.insert(houseSource) < 1) {
            log.error("Insert new house source ERROR!");
            throw new ServiceException(ResponseType.ITEM_ADD_ERR);
        }
    }

    @Override
    public void updateSource(HouseSourceDTO houseSourceDTO) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        HouseSource houseSource = new HouseSource();
        BeanUtils.copyProperties(houseSourceDTO, houseSource);
        houseSource.setUserId(loginUser.getUserId());
        if(houseSourceMapper.updateById(houseSource) < 1) {
            log.error("Update house source ERROR!");
            throw new ServiceException(ResponseType.SERVER_ERR);
        }
    }

    @Override
    public void delSource(Long houseId) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        log.info("Delete source, source:{},userId:{}", houseId, loginUser.getUserId());
        if(houseSourceMapper.selectById(houseId).getUserId().equals(loginUser.getUserId())) {
            if(houseSourceMapper.deleteById(houseId) < 1) {
                log.error("Delete house source ERROR!");
                throw new ServiceException(ResponseType.ITEM_DEL_ERR);
            }
        } else if (loginUser.getRole().equals(Role.admin)) {
            if(houseSourceMapper.deleteById(houseId) < 1) {
                log.error("Delete house source ERROR!");
                throw new ServiceException(ResponseType.ITEM_DEL_ERR);
            }
        } else {
            log.warn("Operation forbidden!");
            throw new ServiceException(ResponseType.UNAUTHORIZED);
        }
    }

    @Override
    public IPage<HouseSourceVO> sourcePage(HouseSourceQuery houseSourceQuery) {
        log.info("select house source by page,query:{}",houseSourceQuery);
        Page<HouseSourceVO> page = new Page<>(houseSourceQuery.getCurrentPage(),houseSourceQuery.getPageSize());
        return houseSourceMapper.sourcePage(page,houseSourceQuery);
    }

    @Override
    public HouseSourceVO sourceDetails(Long houseId) {
        log.info("View house source details. houseId:{}",houseId);
        HouseSource houseSource = houseSourceMapper.selectById(houseId);
        HouseSourceVO houseSourceVO = new HouseSourceVO();
        if(houseSource == null) {
            return null;
        }
        BeanUtils.copyProperties(houseSource,houseSourceVO);
        return houseSourceVO;
    }

    @Override
    public IPage<HouseSourceVO> mySourcePage(HouseSourceQuery houseSourceQuery) {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        houseSourceQuery.setUserId(loginUser.getUserId());
        log.info("select house source by page,query:{}",houseSourceQuery);
        Page<HouseSourceVO> page = new Page<>(houseSourceQuery.getCurrentPage(),houseSourceQuery.getPageSize());
        return houseSourceMapper.sourcePage(page,houseSourceQuery);
    }

    @Override
    public Long selectCount() {
        return houseSourceMapper.selectCount(null);
    }

    @Override
    public Long mySelectCount() {
        CurrentLoginUser loginUser = (CurrentLoginUser) request.getAttribute(Const.CURRENT_LOGIN_USER);
        QueryWrapper<HouseSource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", loginUser.getUserId());
        return houseSourceMapper.selectCount(queryWrapper);
    }
}