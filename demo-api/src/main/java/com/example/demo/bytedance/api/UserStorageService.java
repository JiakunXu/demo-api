package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.user.Result;
import com.example.demo.bytedance.api.bo.user.UserStorage;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface UserStorageService {

    String HTTPS_SET_URL    = "https://developer.toutiao.com/api/apps/set_user_storage";

    String HTTPS_REMOVE_URL = "https://developer.toutiao.com/api/apps/remove_user_storage";

    /**
     * 
     * @param accessToken 服务端 API 调用标识
     * @param openid 登录用户唯一标识
     * @param kvItemList
     * @throws RuntimeException
     */
    Result set(String accessToken, String openid,
               List<UserStorage.KvItem> kvItemList) throws RuntimeException;

    /**
     * 
     * @param accessToken 服务端 API 调用标识
     * @param openid 登录用户唯一标识
     * @param kvItemList
     * @throws RuntimeException
     */
    Result remove(String accessToken, String openid,
                  List<UserStorage.KvItem> kvItemList) throws RuntimeException;

}
