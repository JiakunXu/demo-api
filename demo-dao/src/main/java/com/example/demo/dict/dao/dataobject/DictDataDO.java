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
public class DictDataDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 3136596514473209241L;

    private BigInteger        id;

    private BigInteger        typeId;

    private String            typeValue;

    private String            name;

    private String            value;

    private String            remark;

    private String            status;

    public DictDataDO() {
    }

    public DictDataDO(BigInteger id) {
        this.id = id;
    }

}
