package com.example.demo.framework.bo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author JiakunXu
 */
public class BaseBo extends BaseParameter {

    @JSONField(serialize = false)
    private boolean deleted;

    private String  creator;

    private String  modifier;

    private Date    gmtCreate;

    private Date    gmtModified;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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
