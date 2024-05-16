package com.example.demo.bytedance.api.bo.user;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class UserStorage implements Serializable {

    private static final long serialVersionUID = 6803100618881966448L;

    /**
     * 服务端 API 调用标识.
     */
    @JSONField(name = "access_token")
    private String            accessToken;

    /**
     * 登录用户唯一标识.
     */
    private String            openid;

    /**
     * 用户登录态签名.
     */
    private String            signature;

    /**
     * 用户登录态签名的编码方法.
     */
    @JSONField(name = "sig_method")
    private String            sigMethod;

    @JSONField(name = "kv_list")
    private List<KvItem>      kvItemList;

    @Getter
    @Setter
    public static class KvItem implements Serializable {

        private static final long serialVersionUID = 4109633365245598875L;

        /**
         * 键.
         */
        private String            key;

        /**
         * 值.
         */
        private String            value;

    }

}
