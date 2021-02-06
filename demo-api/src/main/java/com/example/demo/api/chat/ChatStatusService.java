package com.example.demo.api.chat;

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
