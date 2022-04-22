package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.PhoneNumberService;
import com.example.demo.weixin.api.bo.wxa.PhoneNumber;
import com.example.demo.weixin.api.bo.wxa.Result;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberServiceImpl.class);

    @Override
    public PhoneNumber getPhoneNumber(String appid, String accessToken,
                                      String code) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid cannot be null.");
        }

        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code cannot be null.");
        }

        Result result = null;

        try {
            result = JSON.parseObject(
                HttpUtil.post(PhoneNumberService.HTTPS_GET_URL + accessToken, code), Result.class);
        } catch (Exception e) {
            logger.error(accessToken, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        PhoneNumber phoneNumber = result.getPhoneNumber();

        if (phoneNumber == null) {
            throw new RuntimeException("phoneNumber is null.");
        }

        // appid 不匹配
        if (!appid.equals(phoneNumber.getWatermark().getAppid())) {
            throw new RuntimeException("appid not equals.");
        }

        return phoneNumber;
    }

    @Override
    public PhoneNumber getPhoneNumber(String appid, String encryptedData, String sessionKey,
                                      String iv) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid cannot be null.");
        }

        if (StringUtils.isBlank(encryptedData)) {
            throw new RuntimeException("encryptedData cannot be null.");
        }

        if (StringUtils.isBlank(sessionKey)) {
            throw new RuntimeException("sessionKey cannot be null.");
        }

        if (StringUtils.isBlank(iv)) {
            throw new RuntimeException("iv cannot be null.");
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

        PhoneNumber phoneNumber = null;

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
                phoneNumber = JSON.parseObject(result, PhoneNumber.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (phoneNumber == null) {
            throw new RuntimeException("phoneNumber is null.");
        }

        // appid 不匹配
        if (!appid.equals(phoneNumber.getWatermark().getAppid())) {
            throw new RuntimeException("appid not equals.");
        }

        return phoneNumber;
    }

}
