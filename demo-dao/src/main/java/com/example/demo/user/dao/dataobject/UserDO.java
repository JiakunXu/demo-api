package com.example.demo.user.dao.dataobject;

import java.math.BigInteger;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDO extends BaseDO {

    private static final long serialVersionUID = 852047823813545755L;

    private BigInteger        id;

}
