package com.example.demo.chat.dao;

import com.example.demo.api.chat.bo.Chat;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface ChatDao {

    /**
     *
     * @param chat
     * @return
     */
    int countChat(Chat chat);

    /**
     *
     * @param chat
     * @return
     */
    List<Chat> listChats(Chat chat);

    /**
     *
     * @param chat
     * @return
     */
    Chat getChat(Chat chat);

    /**
     *
     * @param chat
     * @return
     */
    int insertChat(Chat chat);

    /**
     *
     * @param chat
     * @return
     */
    int updateChat(Chat chat);

    /**
     *
     * @param chat
     * @return
     */
    int updateUnread(Chat chat);

}
