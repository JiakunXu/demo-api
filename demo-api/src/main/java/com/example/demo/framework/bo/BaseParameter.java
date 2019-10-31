package com.example.demo.framework.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class BaseParameter implements Serializable {

    private static final long serialVersionUID = -2053113219884447756L;

    private String            gmtStart;

    private String            gmtEnd;

    /**
     * 排序字段.
     */
    private String            sort;

    /**
     * 排序类型.
     */
    private String            dir;

    /**
     * 分页号，从1开始.
     */
    @JSONField(serialize = false)
    private int               pageNo;

    @JSONField(serialize = false)
    private int               pageSize;

    @JSONField(serialize = false)
    private int               offset;

    public String getGmtStart() {
        return gmtStart;
    }

    public void setGmtStart(String gmtStart) {
        this.gmtStart = gmtStart;
    }

    public String getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(String gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
