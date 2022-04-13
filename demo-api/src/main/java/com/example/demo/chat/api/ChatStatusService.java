package com.example.demo.chat.api;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public interface ChatStatusService {

    /**
     *
     * @param userId
     * @param friendId
     * @return
     */
    String getStatus(BigInteger userId, String friendId);

}
