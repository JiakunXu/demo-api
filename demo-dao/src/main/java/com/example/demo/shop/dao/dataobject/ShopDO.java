package com.example.demo.shop.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class ShopDO extends BaseDO {

    private static final long serialVersionUID = 1738874407695964779L;

    private BigInteger        id;

    private String            name;

    private String            content;

}
