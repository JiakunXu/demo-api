package com.example.demo.user.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class User extends BaseBO {

    private BigInteger id;

    private String     name;

}
