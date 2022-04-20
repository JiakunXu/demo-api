package com.example.demo.bytedance.api.bo.qrcode;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Background implements Serializable {

    private static final long serialVersionUID = -4682306844587994906L;

    private int               r;

    private int               g;

    private int               b;

}
