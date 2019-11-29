package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.sns.MiniUserInfo;
import com.example.demo.api.weixin.ao.sns.Session;

/**
 * @author JiakunXu
 */
public interface Code2SessionService {

    String HTTPS_CODE_2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    /**
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws RuntimeException
     */
    Session getSession(String appId, String appSecret, String code) throws RuntimeException;

    /**
     *
     * @param appId
     * @param encryptedData 对称解密的目标密文.
     * @param sessionKey 对称解密秘钥.
     * @param iv 对称解密算法初始向量.
     * @return
     * @throws RuntimeException
     */
    MiniUserInfo getUserInfo(String appId, String encryptedData, String sessionKey,
                             String iv) throws RuntimeException;

}
