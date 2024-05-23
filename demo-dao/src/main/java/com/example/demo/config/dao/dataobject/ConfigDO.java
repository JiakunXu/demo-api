package com.example.demo.config.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class ConfigDO extends BaseDO {

    private static final long serialVersionUID = -6484949074708858420L;

    private BigInteger        id;

    /**
     * 名称
     */
    private String            name;

    private String            key;

    private String            value;

    /**
     * 备注
     */
    private String            remark;

    private Boolean           system;

}
