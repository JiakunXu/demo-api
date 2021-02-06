package com.example.demo.api.chat;

import com.example.demo.api.chat.bo.Chat;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface ChatService {

    /**
     *
     * @param userId
     * @return
     */
    int countChat(BigInteger userId);

    /**
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Chat> listChats(BigInteger userId, String pageNo, String pageSize);

    /**
     *
     * @param userId
     * @param friendId
     * @return
     */
    Chat getChat(BigInteger userId, String friendId);

    /**
     *
     * @param userId
     * @param friendId
     * @return
     */
    Chat getChat(BigInteger userId, BigInteger friendId);

    /**
     *
     * @param userId
     * @param friendId
     * @param chat
     * @return
     */
    Chat saveOrUpdate(BigInteger userId, BigInteger friendId, Chat chat);

    /**
     * 已读.
     *
     * @param userId
     * @param id
     * @return
     */
    Chat read(BigInteger userId, BigInteger id);

}
