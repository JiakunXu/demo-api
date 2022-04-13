package com.example.demo.chat.dao.mapper;

import com.example.demo.chat.dao.dataobject.ChatDO;
import com.example.demo.framework.mapper.BaseMapper;

/**
 * @author JiakunXu
 */
public interface ChatMapper extends BaseMapper<ChatDO> {

    /**
     *
     * @param chatDO
     * @return
     */
    int updateChat(ChatDO chatDO);

    /**
     *
     * @param chatDO
     * @return
     */
    int updateUnread(ChatDO chatDO);

}
