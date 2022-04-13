package com.example.demo.subscribe.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.socket.api.bo.Message;
import com.example.demo.subscribe.api.SubscribeService;
import com.example.demo.subscribe.api.bo.Subscribe;
import com.example.demo.subscribe.dao.dataobject.SubscribeDO;
import com.example.demo.tunnel.api.bo.Tunnel;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.subscribe.dao.mapper.SubscribeMapper;
import com.example.demo.tunnel.dao.dataobject.TunnelDO;
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
    private SubscribeMapper     subscribeMapper;

    @Value("${aliyun.ons.topic}")
    private String              topic;

    @Override
    public int countSubscribe(BigInteger userId, String appId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId)) {
            return 0;
        }

        SubscribeDO subscribeDO = new SubscribeDO();
        subscribeDO.setUserId(userId);
        subscribeDO.setAppId(appId);
        subscribeDO.setScene(scene);
        subscribeDO.setSceneId(new BigInteger(sceneId));

        return count0(subscribeDO);
    }

    @Override
    public int countSubscribe(BigInteger userId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId)) {
            return 0;
        }

        SubscribeDO subscribeDO = new SubscribeDO();
        subscribeDO.setUserId(userId);
        subscribeDO.setScene(scene);
        subscribeDO.setSceneId(new BigInteger(sceneId));

        return count1(subscribeDO);
    }

    @Override
    public List<Tunnel> listSubscribes(String scene, String sceneId, Subscribe subscribe) {
        if (StringUtils.isBlank(scene) || StringUtils.isBlank(sceneId) || subscribe == null) {
            return null;
        }

        subscribe.setScene(scene);
        subscribe.setSceneId(new BigInteger(sceneId));

        return BeanUtil.copy(listSubscribes(BeanUtil.copy(subscribe, SubscribeDO.class)),
            Tunnel.class);
    }

    @Override
    public Subscribe getSubscribe(BigInteger userId, String appId, String scene, String sceneId) {
        if (userId == null || StringUtils.isBlank(appId) || StringUtils.isBlank(scene)
            || StringUtils.isBlank(sceneId)) {
            return null;
        }

        SubscribeDO subscribeDO = new SubscribeDO();
        subscribeDO.setUserId(userId);
        subscribeDO.setAppId(appId);
        subscribeDO.setScene(scene);
        subscribeDO.setSceneId(new BigInteger(sceneId));

        return BeanUtil.copy(get(subscribeDO), Subscribe.class);
    }

    @Override
    public Subscribe insertSubscribe(BigInteger userId, String appId, String scene,
                                     String sceneId) {
        if (userId == null || StringUtils.isBlank(appId) || StringUtils.isBlank(scene)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        SubscribeDO subscribeDO = new SubscribeDO();
        subscribeDO.setUserId(userId);
        subscribeDO.setAppId(appId);
        subscribeDO.setScene(scene);

        if ("app".equals(scene)) {
            sceneId = userId.toString();
        }

        if (StringUtils.isBlank(sceneId)) {
            sceneId = "0";
        }

        subscribeDO.setSceneId(new BigInteger(sceneId));
        subscribeDO.setCreator(userId.toString());

        Subscribe s = getSubscribe(userId, appId, scene, sceneId);

        if (s != null) {
            return s;
        }

        try {
            subscribeMapper.insert(subscribeDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribeDO), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        return BeanUtil.copy(subscribeDO, Subscribe.class);
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

    private int count0(SubscribeDO subscribeDO) {
        try {
            return subscribeMapper.count0(subscribeDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribeDO), e);
        }

        return 0;
    }

    private int count1(SubscribeDO subscribeDO) {
        try {
            return subscribeMapper.count1(subscribeDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribeDO), e);
        }

        return 0;
    }

    private List<TunnelDO> listSubscribes(SubscribeDO subscribeDO) {
        try {
            return subscribeMapper.listSubscribes(subscribeDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribeDO), e);
        }

        return null;
    }

    private SubscribeDO get(SubscribeDO subscribeDO) {
        try {
            return subscribeMapper.get(subscribeDO);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(subscribeDO), e);
        }

        return null;
    }

}
