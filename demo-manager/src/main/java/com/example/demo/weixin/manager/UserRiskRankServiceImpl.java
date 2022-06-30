package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UserRiskRankService;
import com.example.demo.weixin.api.bo.wxa.UserInfo;
import com.example.demo.weixin.api.bo.wxa.UserRisk;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class UserRiskRankServiceImpl implements UserRiskRankService {

    private static final Logger logger = LoggerFactory.getLogger(UserRiskRankServiceImpl.class);

    @Override
    public UserRisk getUserRiskRank(String appid, String accessToken,
                                    UserInfo userInfo) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid cannot be null.");
        }

        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (userInfo == null) {
            throw new RuntimeException("user_info cannot be null.");
        }

        userInfo.setAppid(appid);

        UserRisk userRisk;

        try {
            userRisk = JSON.parseObject(HttpUtil
                .post(UserRiskRankService.HTTPS_GET_URL + accessToken, JSON.toJSONString(userInfo)),
                UserRisk.class);
        } catch (Exception e) {
            logger.error(userInfo.toString(), e);

            throw new RuntimeException(e);
        }

        if (userRisk == null) {
            throw new RuntimeException("userRisk is null.");
        }

        if (userRisk.getErrCode() != 0) {
            throw new RuntimeException(userRisk.getErrMsg());
        }

        return userRisk;
    }

}
