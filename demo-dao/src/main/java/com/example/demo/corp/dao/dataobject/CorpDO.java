package com.example.demo.corp.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CorpDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1738874407695964779L;

    private BigInteger        id;

    private String            name;

    public CorpDO(BigInteger id) {
        this.id = id;
    }

}
