package com.example.demo.api.shop.bo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author JiakunXu
 */
public class Shop implements Serializable {

    private BigInteger id;

    private String     name;

    private String     content;

    private Date       gmtCreate;

    private Date       gmtModified;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
