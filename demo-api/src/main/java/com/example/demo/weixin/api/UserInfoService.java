package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.user.UserInfo;
import com.example.demo.weixin.api.bo.user.MiniUserInfo;

/**
 * @author JiakunXu
 */
public interface UserInfoService {

    String HTTPS_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang={2}";

    /**
     * 获取用户基本信息(UnionID机制).
     *
     * @param accessToken 调用接口凭证.
     * @param openid 普通用户的标识，对当前公众号唯一.
     * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语.
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/User_Management/Get_users_basic_information_UnionID.html">微信官方文档</a>
     */
    UserInfo getUserInfo(String accessToken, String openid, String lang) throws RuntimeException;

    /**
     *
     * @param appid
     * @param encryptedData 对称解密的目标密文.
     * @param sessionKey 对称解密秘钥.
     * @param iv 对称解密算法初始向量.
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html">微信官方文档</a>
     */
    MiniUserInfo getUserInfo(String appid, String encryptedData, String sessionKey,
                             String iv) throws RuntimeException;

}
