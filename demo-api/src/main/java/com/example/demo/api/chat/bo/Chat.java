package com.example.demo.api.chat.bo;

import com.example.demo.api.user.bo.User;
import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author JiakunXu
 */
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getFriendId() {
        return friendId;
    }

    public void setFriendId(BigInteger friendId) {
        this.friendId = friendId;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    public BigInteger getChatDetailId() {
        return chatDetailId;
    }

    public void setChatDetailId(BigInteger chatDetailId) {
        this.chatDetailId = chatDetailId;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public ChatDetail getChatDetail() {
        return chatDetail;
    }

    public void setChatDetail(ChatDetail chatDetail) {
        this.chatDetail = chatDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
