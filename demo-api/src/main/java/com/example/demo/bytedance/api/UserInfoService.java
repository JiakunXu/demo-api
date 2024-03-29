package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.user.UserInfo;

/**
 * @author JiakunXu
 */
public interface UserInfoService {

    /**
     *
     * @param appid
     * @param encryptedData 对称解密的目标密文.
     * @param sessionKey 对称解密秘钥.
     * @param iv 对称解密算法初始向量.
     * @return
     * @throws RuntimeException
     */
    UserInfo getUserInfo(String appid, String encryptedData, String sessionKey,
                         String iv) throws RuntimeException;

}
