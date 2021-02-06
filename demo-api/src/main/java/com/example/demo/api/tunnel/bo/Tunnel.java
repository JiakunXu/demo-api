package com.example.demo.api.tunnel.bo;

import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public class Tunnel extends BaseBo {

    private static final long serialVersionUID = -2952991612958654453L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            tunnelId;

    private String            host;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(String tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
