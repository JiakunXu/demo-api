package com.example.demo.api.file.bo;

import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public class File extends BaseBo {

    private static final long serialVersionUID = -989943894629109208L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            name;

    private String            contentType;

    private String            url;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
