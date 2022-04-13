package com.example.demo.chat.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.chat.api.ChatDetailService;
import com.example.demo.chat.api.ChatService;
import com.example.demo.chat.api.ChatStatusService;
import com.example.demo.chat.api.bo.Chat;
import com.example.demo.user.api.UserService;
import com.example.demo.chat.dao.ChatDao;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatDetailService   chatDetailService;

    @Autowired
    private ChatStatusService   chatStatusService;

    @Autowired
    private UserService         userService;

    @Autowired
    private ChatDao             chatDao;

    @Override
    public int countChat(BigInteger userId) {
        if (userId == null) {
            return 0;
        }

        Chat chat = new Chat();
        chat.setUserId(userId);

        return countChat(chat);
    }

    @Override
    public List<Chat> listChats(BigInteger userId, String pageNo, String pageSize) {
        if (userId == null || StringUtils.isBlank(pageNo) || StringUtils.isBlank(pageSize)) {
            return null;
        }

        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setPageNo(Integer.parseInt(pageNo));
        chat.setPageSize(Integer.parseInt(pageSize));

        List<Chat> list = listChats(chat);

        if (list == null || list.size() == 0) {
            return null;
        }

        for (Chat c : list) {
            c.setFriend(userService.getUser(c.getFriendId()));
            c.setChatDetail(chatDetailService.getChatDetail(userId, c.getChatDetailId()));
        }

        return list;
    }

    @Override
    public Chat getChat(BigInteger userId, String friendId) {
        if (userId == null || friendId == null) {
            return null;
        }

        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setFriend(userService.getUser(new BigInteger(friendId)));
        chat.setStatus(chatStatusService.getStatus(userId, friendId));

        return chat;
    }

    @Override
    public Chat getChat(BigInteger userId, BigInteger friendId) {
        if (userId == null || friendId == null) {
            return null;
        }

        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setFriendId(friendId);

        return getChat(chat);
    }

    @Override
    public Chat saveOrUpdate(BigInteger userId, BigInteger friendId, Chat chat) {
        if (userId == null || friendId == null || chat == null) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        chat.setUserId(userId);
        chat.setFriendId(friendId);
        chat.setCreator(userId.toString());
        chat.setModifier(userId.toString());

        try {
            if (chatDao.updateChat(chat) != 1) {
                chatDao.insertChat(chat);
            }
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息更新失败，请稍后再试");
        }

        return chat;
    }

    @Override
    public Chat read(BigInteger userId, BigInteger id) {
        if (userId == null || id == null) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        Chat chat = new Chat();
        chat.setId(id);
        chat.setUserId(userId);
        chat.setUnread(0);
        chat.setModifier(userId.toString());

        try {
            if (chatDao.updateUnread(chat) != 1) {
                throw new ServiceException(Constants.BUSINESS_FAILED);
            }
        } catch (ServiceException e) {
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息不存在");
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat), e);
            throw new ServiceException(Constants.BUSINESS_FAILED, "信息更新失败，请稍后再试");
        }

        return chat;
    }

    private int countChat(Chat chat) {
        try {
            return chatDao.countChat(chat);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat), e);
        }

        return 0;
    }

    private List<Chat> listChats(Chat chat) {
        try {
            return chatDao.listChats(chat);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat), e);
        }

        return null;
    }

    private Chat getChat(Chat chat) {
        try {
            return chatDao.getChat(chat);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(chat), e);
        }

        return null;
    }

}
