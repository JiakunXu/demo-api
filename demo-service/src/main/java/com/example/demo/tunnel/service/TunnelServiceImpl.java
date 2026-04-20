package com.example.demo.tunnel.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.tunnel.api.TunnelService;
import com.example.demo.tunnel.api.bo.Tunnel;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.tunnel.dao.dataobject.TunnelDO;
import com.example.demo.tunnel.dao.mapper.TunnelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class TunnelServiceImpl extends ServiceImpl<TunnelMapper, TunnelDO>
                               implements TunnelService {

    @Override
    public Tunnel insertTunnel(@NotNull BigInteger userId, String host, @NotBlank String creator) {
        TunnelDO tunnelDO = new TunnelDO();
        tunnelDO.setUserId(userId);
        tunnelDO.setTunnelId(UUID.randomUUID().toString());
        tunnelDO.setHost(host);
        tunnelDO.setCreator(creator);

        try {
            this.insert(tunnelDO);
        } catch (Exception e) {
            log.error("{}", tunnelDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return BeanUtil.copy(tunnelDO, Tunnel.class);
    }

    @Override
    public Tunnel deleteTunnel(@NotBlank String tunnelId, @NotBlank String modifier) {
        TunnelDO tunnelDO = new TunnelDO();
        tunnelDO.setTunnelId(tunnelId);
        tunnelDO.setModifier(modifier);

        try {
            this.delete(tunnelDO);
        } catch (Exception e) {
            log.error("{}", tunnelDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(tunnelDO, Tunnel.class);
    }

}
