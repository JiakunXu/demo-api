package com.example.demo.subscribe.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class SubscribeDO extends BaseDO {

    private static final long serialVersionUID = 857172631787085965L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            appId;

    /**
     * 场景.
     */
    private String            scene;

    private BigInteger        sceneId;

}
