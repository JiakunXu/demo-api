package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Activity;

/**
 * @author JiakunXu
 */
public interface UpdatableMessageService {

    String HTTPS_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/activityid/create?access_token={0}&unionid={1}&openid={2}";

    String HTTPS_SEND_URL   = "https://api.weixin.qq.com/cgi-bin/message/wxopen/updatablemsg/send?access_token=";

    /**
     * 创建被分享动态消息或私密消息的 activity_id.
     * 
     * @param accessToken 接口调用凭证
     * @param unionid 为私密消息创建activity_id时，指定分享者为unionid用户。其余用户不能用此activity_id分享私密消息。openid与unionid填一个即可
     * @param openid 为私密消息创建activity_id时，指定分享者为openid用户。其余用户不能用此activity_id分享私密消息。openid与unionid填一个即可
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/updatable-message/updatableMessage.createActivityId.html">微信官方文档</a>
     */
    Activity createActivityId(String accessToken, String unionid,
                              String openid) throws RuntimeException;

    /**
     * 修改被分享的动态消息.
     * 
     * @param accessToken 接口调用凭证
     * @param activityId 动态消息的 ID
     * @param activity
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/updatable-message/updatableMessage.setUpdatableMsg.html">微信官方文档</a>
     */
    BaseResult setUpdatableMsg(String accessToken, String activityId,
                               Activity activity) throws RuntimeException;

}
