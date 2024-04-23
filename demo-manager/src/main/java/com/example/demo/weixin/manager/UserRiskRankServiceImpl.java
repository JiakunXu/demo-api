package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UserRiskRankService;
import com.example.demo.weixin.api.bo.wxa.UserInfo;
import com.example.demo.weixin.api.bo.wxa.UserRisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class UserRiskRankServiceImpl implements UserRiskRankService {

    private static final Logger logger = LoggerFactory.getLogger(UserRiskRankServiceImpl.class);

    @Override
    public UserRisk getUserRiskRank(String appid, String accessToken,
                                    UserInfo userInfo) throws RuntimeException {
        userInfo.setAppid(appid);

        UserRisk userRisk;

        try {
            userRisk = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_GET_URL, accessToken),
                    JSON.toJSONString(userInfo)), UserRisk.class);
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
