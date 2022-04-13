package com.example.demo.chat.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ChatDO extends BaseDO {

    private static final long serialVersionUID = 8356420243427841172L;

    private BigInteger        id;

    private BigInteger        userId;

    private BigInteger        friendId;

    private Date              chatTime;

    private BigInteger        chatDetailId;

    private Integer           unread;

}
