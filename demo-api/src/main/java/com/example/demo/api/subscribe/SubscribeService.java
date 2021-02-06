package com.example.demo.api.subscribe;

import com.example.demo.api.socket.ao.Message;
import com.example.demo.api.subscribe.bo.Subscribe;
import com.example.demo.api.tunnel.bo.Tunnel;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface SubscribeService {

    /**
     *
     * @param userId
     * @param appId
     * @param scene
     * @param sceneId
     * @return
     */
    int countSubscribe(BigInteger userId, String appId, String scene, String sceneId);

    /**
     *
     * @param userId
     * @param scene
     * @param sceneId
     * @return
     */
    int countSubscribe(BigInteger userId, String scene, String sceneId);

    /**
     *
     * @param scene
     * @param sceneId
     * @param subscribe
     * @return
     */
    List<Tunnel> listSubscribes(String scene, String sceneId, Subscribe subscribe);

    /**
     *
     * @param userId
     * @param appId
     * @param scene
     * @param sceneId
     * @return
     */
    Subscribe getSubscribe(BigInteger userId, String appId, String scene, String sceneId);

    /**
     *
     * @param userId
     * @param appId
     * @param scene
     * @param sceneId
     * @return
     */
    Subscribe insertSubscribe(BigInteger userId, String appId, String scene, String sceneId);

    /**
     *
     * @param scene
     * @param sceneId
     * @param message
     */
    void sendMessage(String scene, String sceneId, Message message);

}
