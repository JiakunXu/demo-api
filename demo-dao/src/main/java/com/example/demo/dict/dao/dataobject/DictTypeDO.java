package com.example.demo.dict.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class DictTypeDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -3596786743912293542L;

    private BigInteger        id;

    private String            name;

    private String            value;

    private String            remark;

    private String            status;

    public DictTypeDO() {
    }

    public DictTypeDO(String value) {
        this.value = value;
    }

}
