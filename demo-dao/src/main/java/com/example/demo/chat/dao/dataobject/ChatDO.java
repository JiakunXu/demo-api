package com.example.demo.chat.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class ChatDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 8356420243427841172L;

    private BigInteger        id;

    private BigInteger        userId;

    private BigInteger        friendId;

    private Date              chatTime;

    private BigInteger        chatDetailId;

    private Integer           unread;

}
