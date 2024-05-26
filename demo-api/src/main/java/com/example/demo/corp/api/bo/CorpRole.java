package com.example.demo.corp.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
public class CorpRole extends BaseBO {

    @Serial
    private static final long serialVersionUID = -6062279975639582061L;

    private BigInteger        id;

}
