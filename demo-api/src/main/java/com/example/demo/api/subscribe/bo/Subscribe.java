package com.example.demo.api.subscribe.bo;

import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public class Subscribe extends BaseBo {

    private static final long serialVersionUID = 4067360092344161155L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            appId;

    /**
     * 场景.
     */
    private String            scene;

    private BigInteger        sceneId;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public BigInteger getSceneId() {
        return sceneId;
    }

    public void setSceneId(BigInteger sceneId) {
        this.sceneId = sceneId;
    }

}
