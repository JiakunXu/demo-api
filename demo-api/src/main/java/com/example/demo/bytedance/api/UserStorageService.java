package com.example.demo.bytedance.api;

import com.example.demo.bytedance.api.bo.user.KvItem;
import com.example.demo.bytedance.api.bo.user.UserStorage;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface UserStorageService {

    /**
     * 
     * @param accessToken 服务端 API 调用标识
     * @param openid 登录用户唯一标识
     * @param kvItemList
     * @throws RuntimeException
     */
    void set(String accessToken, String openid, List<KvItem> kvItemList) throws RuntimeException;

    /**
     * 
     * @param accessToken 服务端 API 调用标识
     * @param openid 登录用户唯一标识
     * @param kvItemList
     * @throws RuntimeException
     */
    void remove(String accessToken, String openid, List<KvItem> kvItemList) throws RuntimeException;

}
