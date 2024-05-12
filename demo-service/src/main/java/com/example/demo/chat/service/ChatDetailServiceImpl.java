package com.example.demo.chat.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.chat.api.ChatDetailService;
import com.example.demo.chat.api.bo.Chat;
import com.example.demo.chat.api.bo.ChatDetail;
import com.example.demo.chat.dao.dataobject.ChatDetailDO;
import com.example.demo.chat.dao.mapper.ChatDetailMapper;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
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
    private ChatDetailMapper    chatDetailMapper;

    @Value("${aliyun.ons.topic}")
    private String              topic;

    @Override
    public List<ChatDetail> listChatDetails(BigInteger userId, String id, String friendId,
                                            String pageNo, String pageSize) {
        if (userId == null || StringUtils.isBlank(friendId) || StringUtils.isBlank(pageNo)
            || StringUtils.isBlank(pageSize)) {
            return null;
        }

        ChatDetailDO chatDetailDO = new ChatDetailDO();

        if (StringUtils.isNotBlank(id)) {
            chatDetailDO.setId(new BigInteger(id));
        }

        chatDetailDO.setUserId(userId);
        chatDetailDO.setFriendId(new BigInteger(friendId));
        chatDetailDO.setPageNo(Integer.parseInt(pageNo));
        chatDetailDO.setPageSize(Integer.parseInt(pageSize));

        return BeanUtil.copy(list(chatDetailDO), ChatDetail.class);
    }

    @Override
    public ChatDetail getChatDetail(BigInteger userId, BigInteger id) {
        if (userId == null || id == null) {
            return null;
        }

        ChatDetailDO chatDetailDO = new ChatDetailDO();
        chatDetailDO.setId(id);
        chatDetailDO.setUserId(userId);

        return BeanUtil.copy(get(chatDetailDO), ChatDetail.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ChatDetail insertChatDetail(BigInteger userId, BigInteger friendId, String type,
                                       String content) {
        if (userId == null || friendId == null || StringUtils.isBlank(content)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        String chatId = UUID.randomUUID().toString();

        ChatDetailDO chatDetailDO0 = new ChatDetailDO();
        chatDetailDO0.setChatId(chatId);
        chatDetailDO0.setUserId(userId);
        chatDetailDO0.setFriendId(friendId);
        chatDetailDO0.setFrom("me");
        chatDetailDO0.setType(StringUtils.isBlank(type) ? "text" : type);
        chatDetailDO0.setContent(content);
        chatDetailDO0.setCreator(userId.toString());

        try {
            chatDetailMapper.insert(chatDetailDO0);
        } catch (Exception e) {
            logger.error(chatDetailDO0.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        ChatDetailDO chatDetailDO1 = new ChatDetailDO();
        chatDetailDO1.setChatId(chatId);
        chatDetailDO1.setUserId(friendId);
        chatDetailDO1.setFriendId(userId);
        chatDetailDO1.setFrom("you");
        chatDetailDO1.setType(StringUtils.isBlank(type) ? "text" : type);
        chatDetailDO1.setContent(content);
        chatDetailDO1.setCreator(userId.toString());

        try {
            chatDetailMapper.insert(chatDetailDO1);
        } catch (Exception e) {
            logger.error(chatDetailDO1.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        producerService.send(topic, "chat.message", JSON.toJSONBytes(chatDetailDO1),
            chatDetailDO1.getUserId().toString());

        Date chatTime = new Date();

        Chat chat0 = new Chat();
        chat0.setUserId(chatDetailDO0.getUserId());
        chat0.setFriendId(chatDetailDO0.getFriendId());
        chat0.setChatTime(chatTime);
        chat0.setChatDetailId(chatDetailDO0.getId());
        chat0.setUnread(0);
        producerService.send(topic, "chat.update", JSON.toJSONBytes(chat0), chatId);

        Chat chat1 = new Chat();
        chat1.setUserId(chatDetailDO1.getUserId());
        chat1.setFriendId(chatDetailDO1.getFriendId());
        chat1.setChatTime(chatTime);
        chat1.setChatDetailId(chatDetailDO1.getId());
        chat1.setUnread(1);
        producerService.send(topic, "chat.update", JSON.toJSONBytes(chat1), chatId);

        return BeanUtil.copy(chatDetailDO0, ChatDetail.class);
    }

    private List<ChatDetailDO> list(ChatDetailDO chatDetailDO) {
        try {
            return chatDetailMapper.list(chatDetailDO);
        } catch (Exception e) {
            logger.error(chatDetailDO.toString(), e);
        }

        return null;
    }

    private ChatDetailDO get(ChatDetailDO chatDetailDO) {
        try {
            return chatDetailMapper.get(chatDetailDO);
        } catch (Exception e) {
            logger.error(chatDetailDO.toString(), e);
        }

        return null;
    }

}
