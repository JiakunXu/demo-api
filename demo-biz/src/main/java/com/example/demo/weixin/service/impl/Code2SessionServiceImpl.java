package com.example.demo.weixin.service.impl;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.weixin.Code2SessionService;
import com.example.demo.api.weixin.ao.sns.MiniUserInfo;
import com.example.demo.api.weixin.ao.sns.Session;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appId, String appSecret, String code) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret 公众号的appsecret 不能为空.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code code参数 不能为空.");
        }

        StringBuilder sb = new StringBuilder(Code2SessionService.HTTPS_CODE_2_SESSION_URL);
        sb.append("&appid=").append(appId).append("&secret=").append(appSecret).append("&js_code=")
            .append(code);

        Session session = null;

        try {
            session = JSON.parseObject(HttpUtil.get(sb.toString()), Session.class);
        } catch (Exception e) {
            logger.error(sb.toString(), e);

            throw new RuntimeException("HttpUtil error.");
        }

        if (session == null) {
            throw new RuntimeException("session is null.");
        }

        String errCode = session.getErrCode();
        if (StringUtils.isNotBlank(errCode)) {
            logger.error(JSON.toJSONString(session));

            throw new RuntimeException(session.getErrMsg());
        }

        return session;
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
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
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
