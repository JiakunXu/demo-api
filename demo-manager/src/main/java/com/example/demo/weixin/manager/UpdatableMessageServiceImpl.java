package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UpdatableMessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class UpdatableMessageServiceImpl implements UpdatableMessageService {

    private static final Logger logger = LoggerFactory.getLogger(UpdatableMessageServiceImpl.class);

    @Override
    public Activity createActivityId(String accessToken, String unionid,
                                     String openid) throws RuntimeException {
        Activity activity;

        try {
            activity = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_CREATE_URL, accessToken, unionid, openid)),
                Activity.class);
        } catch (Exception e) {
            logger.error(accessToken + "&" + unionid + "&" + openid, e);

            throw new RuntimeException(e);
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
        if (activity == null) {
            throw new RuntimeException("activity cannot be null.");
        }

        activity.setActivityId(activityId);

        BaseResult result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(HTTPS_SEND_URL + accessToken, JSON.toJSONString(activity)),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(activity.toString(), e);

            throw new RuntimeException(e);
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
