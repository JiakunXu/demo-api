package com.example.demo.chat.api;

import com.example.demo.chat.api.bo.ChatDetail;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface ChatDetailService {

    /**
     *
     * @param userId
     * @param id
     * @param friendId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ChatDetail> listChatDetails(BigInteger userId, String id, String friendId, String pageNo,
                                     String pageSize);

    /**
     *
     * @param userId
     * @param id
     * @return
     */
    ChatDetail getChatDetail(BigInteger userId, BigInteger id);

    /**
     *
     * @param userId
     * @param friendId
     * @param type
     * @param content
     * @return
     */
    ChatDetail insertChatDetail(BigInteger userId, BigInteger friendId, String type,
                                String content);

}
