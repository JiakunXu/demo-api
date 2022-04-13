package com.example.demo.weixin.service.impl;

import com.example.demo.api.weixin.ao.sns.MiniUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.weixin.UserInfoService;
import com.example.demo.api.weixin.ao.UserInfo;
import com.example.demo.framework.util.HttpUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @author JiakunXu
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Override
    public UserInfo getUserInfo(String accessToken, String openId,
                                String lang) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openid cannot be null.");
        }

        lang = StringUtils.isBlank(lang) ? "zh_CN" : lang;

        UserInfo userInfo = null;

        try {
            userInfo = JSON.parseObject(
                HttpUtil.get(UserInfoService.HTTPS_USER_INFO_URL
                    .replace("$ACCESS_TOKEN$", accessToken.trim())
                    .replace("$OPENID$", openId.trim()).replace("$LANG$", lang.trim())),
                UserInfo.class);
        } catch (Exception e) {
            logger.error(accessToken + "&" + openId, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userinfo is null.");
        }

        String errCode = userInfo.getErrCode();
        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(userInfo.getErrMsg());
        }

        return userInfo;
    }

    @Override
    public MiniUserInfo getUserInfo(String appId, String encryptedData, String sessionKey,
                                    String iv) {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(encryptedData)) {
            throw new RuntimeException("encryptedData encryptedData参数 不能为空.");
        }

        if (StringUtils.isBlank(sessionKey)) {
            throw new RuntimeException("sessionKey sessionKey参数 不能为空.");
        }

        if (StringUtils.isBlank(iv)) {
            throw new RuntimeException("iv iv参数 不能为空.");
        }

        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }

        MiniUserInfo userInfo = null;

        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);

            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                userInfo = JSON.parseObject(result, MiniUserInfo.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userInfo is null.");
        }

        // appId 不匹配
        if (!appId.equals(userInfo.getWatermark().getAppId())) {
            throw new RuntimeException("appId not equals.");
        }

        return userInfo;
    }

}
