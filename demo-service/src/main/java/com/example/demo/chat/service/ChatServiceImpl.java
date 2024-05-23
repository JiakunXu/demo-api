package com.example.demo.chat.service;

import com.example.demo.chat.api.ChatDetailService;
import com.example.demo.chat.api.ChatService;
import com.example.demo.chat.api.ChatStatusService;
import com.example.demo.chat.api.bo.Chat;
import com.example.demo.chat.dao.dataobject.ChatDO;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.user.api.UserService;
import com.example.demo.chat.dao.mapper.ChatMapper;
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
    private ChatMapper          chatMapper;

    @Override
    public int countChat(BigInteger userId) {
        if (userId == null) {
            return 0;
        }

        ChatDO chatDO = new ChatDO();
        chatDO.setUserId(userId);

        return count(chatDO);
    }

    @Override
    public List<Chat> listChats(BigInteger userId, String pageNo, String pageSize) {
        if (userId == null || StringUtils.isBlank(pageNo) || StringUtils.isBlank(pageSize)) {
            return null;
        }

        ChatDO chatDO = new ChatDO();
        chatDO.setUserId(userId);
        chatDO.setPageNo(Integer.parseInt(pageNo));
        chatDO.setPageSize(Integer.parseInt(pageSize));

        List<Chat> list = BeanUtil.copy(list(chatDO), Chat.class);

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

        ChatDO chatDO = new ChatDO();
        chatDO.setUserId(userId);
        chatDO.setFriendId(friendId);

        return BeanUtil.copy(get(chatDO), Chat.class);
    }

    @Override
    public Chat saveOrUpdate(BigInteger userId, BigInteger friendId, Chat chat) {
        if (userId == null || friendId == null || chat == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        chat.setUserId(userId);
        chat.setFriendId(friendId);

        ChatDO chatDO = BeanUtil.copy(chat, ChatDO.class);
        chatDO.setCreator(userId.toString());
        chatDO.setModifier(userId.toString());

        try {
            if (chatMapper.updateChat(chatDO) != 1) {
                chatMapper.insert(chatDO);
            }
        } catch (Exception e) {
            logger.error(chatDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return chat;
    }

    @Override
    public Chat read(BigInteger userId, BigInteger id) {
        if (userId == null || id == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        Chat chat = new Chat();
        chat.setId(id);
        chat.setUserId(userId);
        chat.setUnread(0);

        ChatDO chatDO = BeanUtil.copy(chat, ChatDO.class);
        chatDO.setModifier(userId.toString());

        try {
            if (chatMapper.updateUnread(chatDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息不存在");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(chatDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return chat;
    }

    private int count(ChatDO chatDO) {
        try {
            return chatMapper.count(chatDO);
        } catch (Exception e) {
            logger.error(chatDO.toString(), e);
        }

        return 0;
    }

    private List<ChatDO> list(ChatDO chatDO) {
        try {
            return chatMapper.list(chatDO);
        } catch (Exception e) {
            logger.error(chatDO.toString(), e);
        }

        return null;
    }

    private ChatDO get(ChatDO chatDO) {
        try {
            return chatMapper.get(chatDO);
        } catch (Exception e) {
            logger.error(chatDO.toString(), e);
        }

        return null;
    }

}
