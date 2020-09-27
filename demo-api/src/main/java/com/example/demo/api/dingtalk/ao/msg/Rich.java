package com.example.demo.api.dingtalk.ao.msg;

import java.io.Serializable;

/**
 * 单行富文本信息.
 *
 * @author JiakunXu
 */
public class Rich implements Serializable {

    private static final long serialVersionUID = -5246429195106842539L;

    /**
     * 单行富文本信息的数目.
     */
    private String            num;

    /**
     * 单行富文本信息的单位.
     */
    private String            unit;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
