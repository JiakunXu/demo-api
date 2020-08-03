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

    private Date              createTime;

    private Date              updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
