package com.example.demo.api.chat.bo;

import com.example.demo.api.user.bo.User;
import com.example.demo.framework.bo.BaseBo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Chat extends BaseBo {

    private static final long serialVersionUID = 1355490942757094850L;

    private BigInteger        id;

    private BigInteger        userId;

    private BigInteger        friendId;

    private Date              chatTime;

    private BigInteger        chatDetailId;

    private Integer           unread;

    // 👇👇👇👇👇 其他 👇👇👇👇👇

    private User              friend;

    private ChatDetail        chatDetail;

    private String            status;

}
