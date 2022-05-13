package com.example.demo.dingtalk.api.bo.user;

import com.example.demo.dingtalk.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class UserInfo extends BaseResult {

    private static final long serialVersionUID = -5832268294591250823L;

    private String            userId;

}
