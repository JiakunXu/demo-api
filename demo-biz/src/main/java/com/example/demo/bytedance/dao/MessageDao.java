package com.example.demo.bytedance.dao;

import com.example.demo.api.bytedance.bo.Message;

/**
 * @author JiakunXu
 */
public interface MessageDao {

    /**
     *
     * @param message
     * @return
     */
    int insertMessage(Message message);

}
