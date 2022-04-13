package com.example.demo.subscribe.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.socket.api.ao.Message;
import com.example.demo.subscribe.api.SubscribeService;
import com.example.demo.subscribe.api.bo.Subscribe;
import com.example.demo.tunnel.api.bo.Tunnel;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.subscribe.dao.SubscribeDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {

    private static final Logger logger = LoggerFactory.getLogger(SubscribeServiceImpl.class);

    @Autowired
    private ProducerService     producerService;

    @Autowired
    private SubscribeDao        subscribeDao;

    @Value("${aliyun.ons.topic}")
    private String              topic;

    @Override
    public int countSubscribe(BigInteger userId, String appId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId)) {
            return 0;
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setAppId(appId);
        subscribe.setScene(scene);
        subscribe.setSceneId(new BigInteger(sceneId));

        return countSubscribe0(subscribe);
    }

    @Override
    public int countSubscribe(BigInteger userId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId)) {
            return 0;
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setScene(scene);
        subscribe.setSceneId(new BigInteger(sceneId));

        return countSubscribe1(subscribe);
    }

    @Override
    public List<Tunnel> listSubscribes(String scene, String sceneId, Subscribe subscribe) {
        if (StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId) || subscribe == null) {
            return null;
        }

        subscribe.setScene(scene);
        subscribe.setSceneId(new BigInteger(sceneId));

        return listSubscribes(subscribe);
    }

    @Override
    public Subscribe getSubscribe(BigInteger userId, String appId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(appId) || StringUtils.isBlank(scene)
            || StringUtils.isBlank(sceneId)) {
            return null;
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setAppId(appId);
        subscribe.setScene(scene);
        subscribe.setSceneId(new BigInteger(sceneId));

        return getSubscribe(subscribe);
    }

    @Override
    public Subscribe insertSubscribe(BigInteger userId, String appId, String scene,
                                     String sceneId) {
        if (userId == null || StringUtils.isBlank(appId) || StringUtils.isBlank(scene)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);
        subscribe.setAppId(appId);
        subscribe.setScene(scene);

        if ("app".equals(scene)) {
            sceneId = userId.toString();
        }

        if (StringUtils.isBlank(sceneId)) {
            sceneId = "0";
        }

        subscribe.setSceneId(new BigInteger(sceneId));
        subscribe.setCreator(userId.toString());

        Subscribe s = getSubscribe(userId, appId, scene, sceneId);

        if (s != null) {
            return s;
        }

        try {
            subscribeDao.insertSubscribe(subscribe);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribe), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        return subscribe;
    }

    @Override
    public void sendMessage(String scene, String sceneId, Message message) {
        if (StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId) || message == null) {
            return;
        }

        for (int i = 1;; i++) {
            Subscribe subscribe = new Subscribe();
            subscribe.setPageNo(i);
            subscribe.setPageSize(20);

            List<Tunnel> list = listSubscribes(scene, sceneId, subscribe);

            if (list == null || list.size() == 0) {
                return;
            }

            for (Tunnel tunnel : list) {
                producerService.sendOneway(topic, "web.socket", JSON.toJSONBytes(message),
                    tunnel.getTunnelId());
            }

            if (list.size() < 20) {
                return;
            }
        }
    }

    private int countSubscribe0(Subscribe subscribe) {
        try {
            return subscribeDao.countSubscribe0(subscribe);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribe), e);
        }

        return 0;
    }

    private int countSubscribe1(Subscribe subscribe) {
        try {
            return subscribeDao.countSubscribe1(subscribe);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribe), e);
        }

        return 0;
    }

    private List<Tunnel> listSubscribes(Subscribe subscribe) {
        try {
            return subscribeDao.listSubscribes(subscribe);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribe), e);
        }

        return null;
    }

    private Subscribe getSubscribe(Subscribe subscribe) {
        try {
            return subscribeDao.getSubscribe(subscribe);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribe), e);
        }

        return null;
    }

}
