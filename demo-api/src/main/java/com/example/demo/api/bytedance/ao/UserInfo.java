package com.example.demo.api.bytedance.ao;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7199131797406817934L;

    /**
     * 用户昵称.
     */
    private String            nickName;

    /**
     * 用户头像网络地址.
     */
    private String            avatarUrl;

    /**
     * 用户性别，0: 未知；1:男性；2:女性.
     */
    private Integer           gender;

    /**
     * 用户所在城市.
     */
    private String            city;

    /**
     * 用户所在省份.
     */
    private String            province;

    /**
     * 用户所在国家.
     */
    private String            country;

    /**
     * 用户 openId.
     */
    private String            openId;

    /**
     * 敏感数据水印.
     */
    private Watermark         watermark;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Watermark getWatermark() {
        return watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }
}
