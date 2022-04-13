package com.example.demo.chat.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.aliyun.ProducerService;
import com.example.demo.api.chat.ChatDetailService;
import com.example.demo.api.chat.bo.Chat;
import com.example.demo.api.chat.bo.ChatDetail;
import com.example.demo.api.user.bo.User;
import com.example.demo.chat.dao.ChatDetailDao;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class ChatDetailServiceImpl implements ChatDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ChatDetailServiceImpl.class);

    @Autowired
    private ProducerService     producerService;

    @Autowired
    private ChatDetailDao       chatDetailDao;

    @Value("${aliyun.ons.topic}")
    private String              topic;

    @Override
    public List<ChatDetail> listChatDetails(BigInteger userId, String id, String friendId,
                                            String pageNo, String pageSize) {
        if (userId == null || StringUtils.isBlank(friendId) || StringUtils.isBlank(pageNo)
            || StringUtils.isBlank(pageSize)) {
            return null;
        }

        ChatDetail chatDetail = new ChatDetail();

        if (StringUtils.isNotBlank(id)) {
            chatDetail.setId(new BigInteger(id));
        }

        chatDetail.setUserId(userId);
        chatDetail.setFriendId(new BigInteger(friendId));
        chatDetail.setPageNo(Integer.parseInt(pageNo));
        chatDetail.setPageSize(Integer.parseInt(pageSize));

        return listChatDetails(chatDetail);
    }

    @Override
    public ChatDetail getChatDetail(BigInteger userId, BigInteger id) {
        if (userId == null || id == null) {
            return null;
        }

        ChatDetail chatDetail = new ChatDetail();
        chatDetail.setId(id);
        chatDetail.setUserId(userId);

        return getChatDetail(chatDetail);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ChatDetail insertChatDetail(BigInteger userId, BigInteger friendId, String type,
                                       String content) {
        if (userId == null || friendId == null || StringUtils.isBlank(content)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        String chatId = UUID.randomUUID().toString();

        ChatDetail chatDetail0 = new ChatDetail();
        chatDetail0.setChatId(chatId);
        chatDetail0.setUserId(userId);
        chatDetail0.setFriendId(friendId);
        chatDetail0.setFrom("me");
        chatDetail0.setType(StringUtils.isBlank(type) ? "text" : type);
        chatDetail0.setContent(content);
        chatDetail0.setCreator(userId.toString());

        try {
            chatDetailDao.insertChatDetail(chatDetail0);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chatDetail0), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        ChatDetail chatDetail1 = new ChatDetail();
        chatDetail1.setChatId(chatId);
        chatDetail1.setUserId(friendId);
        chatDetail1.setFriendId(userId);
        chatDetail1.setFrom("you");
        chatDetail1.setType(StringUtils.isBlank(type) ? "text" : type);
        chatDetail1.setContent(content);
        chatDetail1.setCreator(userId.toString());

        try {
            chatDetailDao.insertChatDetail(chatDetail1);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chatDetail1), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息创建失败，请稍后再试");
        }

        producerService.sendOneway(topic, "chat.message", JSON.toJSONBytes(chatDetail1),
            chatDetail1.getUserId().toString());

        Date chatTime = new Date();

        Chat chat0 = new Chat();
        chat0.setUserId(chatDetail0.getUserId());
        chat0.setFriendId(chatDetail0.getFriendId());
        chat0.setChatTime(chatTime);
        chat0.setChatDetailId(chatDetail0.getId());
        chat0.setUnread(0);
        producerService.sendOneway(topic, "chat.update", JSON.toJSONBytes(chat0), chatId);

        Chat chat1 = new Chat();
        chat1.setUserId(chatDetail1.getUserId());
        chat1.setFriendId(chatDetail1.getFriendId());
        chat1.setChatTime(chatTime);
        chat1.setChatDetailId(chatDetail1.getId());
        chat1.setUnread(1);
        producerService.sendOneway(topic, "chat.update", JSON.toJSONBytes(chat1), chatId);

        return chatDetail0;
    }

    private List<ChatDetail> listChatDetails(ChatDetail chatDetail) {
        try {
            return chatDetailDao.listChatDetails(chatDetail);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chatDetail), e);
        }

        return null;
    }

    private ChatDetail getChatDetail(ChatDetail chatDetail) {
        try {
            return chatDetailDao.getChatDetail(chatDetail);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chatDetail), e);
        }

        return null;
    }

}
