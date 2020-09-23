package com.example.demo.api.weixin.ao;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class LineColor implements Serializable {

    private static final long serialVersionUID = -4682306844587994906L;

    private int               r;

    private int               g;

    private int               b;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
