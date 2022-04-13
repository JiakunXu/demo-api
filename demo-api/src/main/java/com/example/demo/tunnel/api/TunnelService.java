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
     * @return
     */
    Tunnel insertTunnel(BigInteger userId, String host);

    /**
     *
     * @param tunnelId
     * @param modifier
     * @return
     */
    Tunnel deleteTunnel(String tunnelId, String modifier);

}
