package com.example.demo.api.dingtalk.ao;

/**
 * @author JiakunXu
 */
public class UserInfo extends BaseResult {

    private static final long serialVersionUID = -5832268294591250823L;

    private String            userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
