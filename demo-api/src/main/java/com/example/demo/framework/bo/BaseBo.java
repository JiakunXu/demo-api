package com.example.demo.framework.bo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author JiakunXu
 */
public class BaseBo extends BaseParameter {

    private static final long serialVersionUID = 6425935261415820282L;

    @JSONField(serialize = false)
    private Boolean           deleted;

    private String            creator;

    private String            modifier;

    private Date              gmtCreate;

    private Date              gmtModified;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
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
