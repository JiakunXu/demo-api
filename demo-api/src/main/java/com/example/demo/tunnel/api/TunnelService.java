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
     * @param creator
     * @return
     */
    Tunnel insertTunnel(BigInteger userId, String host, String creator);

    /**
     *
     * @param tunnelId
     * @param modifier
     * @return
     */
    Tunnel deleteTunnel(String tunnelId, String modifier);

}
