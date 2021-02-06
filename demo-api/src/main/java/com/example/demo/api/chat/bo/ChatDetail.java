package com.example.demo.api.chat.bo;

import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}