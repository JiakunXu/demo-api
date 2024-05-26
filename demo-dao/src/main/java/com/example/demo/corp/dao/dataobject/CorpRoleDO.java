package com.example.demo.corp.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class CorpRoleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 5088340132394345083L;

    private BigInteger        id;

}
