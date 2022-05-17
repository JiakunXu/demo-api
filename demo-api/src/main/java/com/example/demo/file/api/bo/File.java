package com.example.demo.file.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class File extends BaseBO {

    private static final long serialVersionUID = -989943894629109208L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            name;

    private String            contentType;

    private String            url;

}
