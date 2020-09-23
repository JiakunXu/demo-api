package com.example.demo.api.dingtalk.ao;

/**
 * @author JiakunXu
 */
public class User extends BaseResult {

    private static final long serialVersionUID = -4673368456299605352L;

    private String            userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
