package com.example.demo.tunnel.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.tunnel.TunnelService;
import com.example.demo.api.tunnel.bo.Tunnel;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.tunnel.dao.TunnelDao;
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
    private TunnelDao           tunnelDao;

    @Override
    public Tunnel insertTunnel(BigInteger userId, String host) {
        if (userId == null) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        Tunnel tunnel = new Tunnel();
        tunnel.setUserId(userId);
        tunnel.setTunnelId(UUID.randomUUID().toString());
        tunnel.setHost(host);
        tunnel.setCreator(userId.toString());

        try {
            tunnelDao.insertTunnel(tunnel);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(tunnel), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        return tunnel;
    }

    @Override
    public Tunnel deleteTunnel(String tunnelId, String modifier) {
        if (StringUtils.isBlank(tunnelId)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        Tunnel tunnel = new Tunnel();
        tunnel.setTunnelId(tunnelId);
        tunnel.setModifier(modifier);

        try {
            if (tunnelDao.deleteTunnel(tunnel) != 1) {
                throw new ServiceException(Constants.BUSINESS_FAILED);
            }
        } catch (ServiceException e) {
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息不存在");
        } catch (Exception e) {
            logger.error(JSON.toJSONString(tunnel), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息更新失败，请稍后再试");
        }

        return tunnel;
    }

}
