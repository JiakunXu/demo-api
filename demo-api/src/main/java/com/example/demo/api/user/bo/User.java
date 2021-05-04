package com.example.demo.api.user.bo;

import com.example.demo.framework.bo.BaseBo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class User extends BaseBo {

    private BigInteger id;

    private String     name;

}
