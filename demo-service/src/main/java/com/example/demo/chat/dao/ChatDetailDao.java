package com.example.demo.chat.dao;

import com.example.demo.chat.api.bo.ChatDetail;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface ChatDetailDao {

    /**
     *
     * @param chatDetail
     * @return
     */
    List<ChatDetail> listChatDetails(ChatDetail chatDetail);

    /**
     *
     * @param chatDetail
     * @return
     */
    ChatDetail getChatDetail(ChatDetail chatDetail);

    /**
     *
     * @param chatDetail
     * @return
     */
    int insertChatDetail(ChatDetail chatDetail);

}
