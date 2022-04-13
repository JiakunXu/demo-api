package com.example.demo.tunnel.dao;

import com.example.demo.api.tunnel.bo.Tunnel;

/**
 * @author JiakunXu
 */
public interface TunnelDao {

    /**
     *
     * @param tunnel
     * @return
     */
    int insertTunnel(Tunnel tunnel);

    /**
     *
     * @param tunnel
     * @return
     */
    int deleteTunnel(Tunnel tunnel);

}
