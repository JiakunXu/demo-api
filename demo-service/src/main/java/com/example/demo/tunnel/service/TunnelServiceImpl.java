package com.example.demo.tunnel.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.tunnel.api.TunnelService;
import com.example.demo.tunnel.api.bo.Tunnel;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.tunnel.dao.dataobject.TunnelDO;
import com.example.demo.tunnel.dao.mapper.TunnelMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class TunnelServiceImpl implements TunnelService {

    private static final Logger logger = LoggerFactory.getLogger(TunnelServiceImpl.class);

    @Autowired
    private TunnelMapper        tunnelMapper;

    @Override
    public Tunnel insertTunnel(BigInteger userId, String host) {
        if (userId == null) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        TunnelDO tunnelDO = new TunnelDO();
        tunnelDO.setUserId(userId);
        tunnelDO.setTunnelId(UUID.randomUUID().toString());
        tunnelDO.setHost(host);
        tunnelDO.setCreator(userId.toString());

        try {
            tunnelMapper.insert(tunnelDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(tunnelDO), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        return BeanUtil.copy(tunnelDO, Tunnel.class);
    }

    @Override
    public Tunnel deleteTunnel(String tunnelId, String modifier) {
        if (StringUtils.isBlank(tunnelId)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        TunnelDO tunnelDO = new TunnelDO();
        tunnelDO.setTunnelId(tunnelId);
        tunnelDO.setModifier(modifier);

        try {
            if (tunnelMapper.delete(tunnelDO) != 1) {
                throw new ServiceException(Constants.BUSINESS_FAILED);
            }
        } catch (ServiceException e) {
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息不存在");
        } catch (Exception e) {
            logger.error(JSON.toJSONString(tunnelDO), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(tunnelDO, Tunnel.class);
    }

}
