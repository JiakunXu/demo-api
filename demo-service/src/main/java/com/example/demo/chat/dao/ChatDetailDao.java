package com.example.demo.chat.dao;

import com.example.demo.api.chat.bo.ChatDetail;

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
