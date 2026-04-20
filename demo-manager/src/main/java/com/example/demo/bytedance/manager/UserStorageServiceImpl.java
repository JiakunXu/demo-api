package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.UserStorageService;
import com.example.demo.bytedance.api.bo.user.Result;
import com.example.demo.bytedance.api.bo.user.UserStorage;
import com.example.demo.framework.util.EncryptUtil;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class UserStorageServiceImpl implements UserStorageService {

    @Override
    public Result set(String accessToken, String openid,
                      List<UserStorage.KvItem> kvItemList) throws RuntimeException {
        UserStorage userStorage = new UserStorage();
        userStorage.setAccessToken(accessToken);
        userStorage.setOpenid(openid);
        userStorage.setSigMethod("hmac_sha256");
        userStorage.setKvItemList(kvItemList);

        try {
            userStorage.setSignature(
                EncryptUtil.encryptHmac(JSON.toJSONString(userStorage), "session_key"));
        } catch (IOException e) {
            log.error("{}", userStorage, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        Result result;

        try {
            result = JSON.parseObject(
                HttpUtil.download(UserStorageService.HTTPS_SET_URL, JSON.toJSONString(userStorage)),
                Result.class);
        } catch (Exception e) {
            log.error("{}", userStorage, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Result remove(String accessToken, String openid,
                         List<UserStorage.KvItem> kvItemList) throws RuntimeException {
        UserStorage userStorage = new UserStorage();
        userStorage.setAccessToken(accessToken);
        userStorage.setOpenid(openid);
        userStorage.setSigMethod("hmac_sha256");
        userStorage.setKvItemList(kvItemList);

        try {
            userStorage.setSignature(
                EncryptUtil.encryptHmac(JSON.toJSONString(userStorage), "session_key"));
        } catch (IOException e) {
            log.error("{}", userStorage, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        Result result;

        try {
            result = JSON.parseObject(HttpUtil.download(UserStorageService.HTTPS_REMOVE_URL,
                JSON.toJSONString(userStorage)), Result.class);
        } catch (Exception e) {
            log.error("{}", userStorage, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return result;
    }

}
