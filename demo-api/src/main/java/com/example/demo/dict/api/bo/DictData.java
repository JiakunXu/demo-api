package com.example.demo.dict.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class DictData extends BaseBO {

    @Serial
    private static final long serialVersionUID = -8188973827667833073L;

    private BigInteger        id;

    private BigInteger        typeId;

    private String            typeValue;

    private String            name;

    private String            value;

    private String            remark;

    private String            status;

    private String[]          typeValues;

    public DictData() {

    }

    public DictData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public enum Status {
                        /**
                         * 启用/停用
                         */
                        ENABLE("E", "启用"), DISABLE("D", "停用");

        public final String value;

        public final String desc;

        Status(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
