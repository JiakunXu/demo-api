package com.example.demo.dingtalk.api.bo.process;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormValue implements Serializable {

    private static final long serialVersionUID = 1525681899036768310L;

    private String            name;

    private String            value;

    private String            extValue;

    public FormValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public FormValue(String name, String value, String extValue) {
        this.name = name;
        this.value = value;
        this.extValue = extValue;
    }

}
