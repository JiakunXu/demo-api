package com.example.demo.api.chat.bo;

import com.example.demo.framework.bo.BaseBo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ChatDetail extends BaseBo {

    private static final long serialVersionUID = 1355490942757094850L;

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