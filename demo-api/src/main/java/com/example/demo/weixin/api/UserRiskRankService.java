package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.UserInfo;
import com.example.demo.weixin.api.bo.wxa.UserRisk;

/**
 * @author JiakunXu
 */
public interface UserRiskRankService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/wxa/getuserriskrank?access_token={0}";

    /**
     * 根据提交的用户信息数据获取用户的安全等级 risk_rank，无需用户授权.
     * 
     * @param appid
     * @param accessToken
     * @param userInfo
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/safety-control-capability/riskControl.getUserRiskRank.html">微信官方文档</a>
     */
    UserRisk getUserRiskRank(String appid, String accessToken,
                             UserInfo userInfo) throws RuntimeException;

}
