package com.example.demo.chat.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ChatDetailDO extends BaseDO {

    private static final long serialVersionUID = 4553378337957619071L;

    private BigInteger        id;

    private String            chatId;

    private BigInteger        userId;

    private BigInteger        friendId;

    /**
     * 我 me
     * 你 you
     */
    private String            from;

    private String            type;

    private String            content;

}
