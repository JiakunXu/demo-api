package com.example.demo.tunnel.api;

import com.example.demo.tunnel.api.bo.Tunnel;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public interface TunnelService {

    /**
     * 
     * @param userId
     * @param host
     * @param createBy
     * @return
     */
    Tunnel insertTunnel(BigInteger userId, String host, String createBy);

    /**
     *
     * @param tunnelId
     * @param updateBy
     * @return
     */
    Tunnel deleteTunnel(String tunnelId, String updateBy);

}
