package com.example.demo.api.weixin.ao;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class LineColor implements Serializable {

    private static final long serialVersionUID = -4682306844587994906L;

    private int               r;

    private int               g;

    private int               b;

}
