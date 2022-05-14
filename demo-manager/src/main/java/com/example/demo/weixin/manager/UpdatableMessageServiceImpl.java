package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UpdatableMessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Activity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class UpdatableMessageServiceImpl implements UpdatableMessageService {

    private static final Logger logger = LoggerFactory.getLogger(UpdatableMessageServiceImpl.class);

    @Override
    public Activity createActivityId(String accessToken, String unionid,
                                     String openid) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(unionid) && StringUtils.isBlank(openid)) {
            throw new RuntimeException("unionid && openid cannot be null.");
        }

        Activity activity = null;

        try {
            activity = JSON.parseObject(
                HttpUtil.get(UpdatableMessageService.HTTPS_CREATE_URL + accessToken + "&unionid="
                             + unionid + "&openid=" + openid),
                Activity.class);
        } catch (Exception e) {
            logger.error(unionid + "&" + openid, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (activity == null) {
            throw new RuntimeException("activity is null.");
        }

        if (activity.getErrCode() != 0) {
            throw new RuntimeException(activity.getErrMsg());
        }

        return activity;
    }

    @Override
    public BaseResult setUpdatableMsg(String accessToken, String activityId,
                                      Activity activity) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(activityId)) {
            throw new RuntimeException("activity_id cannot be null.");
        }

        if (activity == null) {
            throw new RuntimeException("activity cannot be null.");
        }

        activity.setActivityId(activityId);

        BaseResult result = null;

        try {
            result = JSON
                .parseObject(HttpUtil.post(UpdatableMessageService.HTTPS_SEND_URL + accessToken,
                    JSON.toJSONString(activity)), BaseResult.class);
        } catch (Exception e) {
            logger.error(activity.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
