package com.example.demo.weixin.api.bo.message;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Keyword2 extends Value {

    private static final long serialVersionUID = -3139515201876777315L;

    public Keyword2() {
    }

    public Keyword2(String value) {
        this.setValue(value);
    }

    public Keyword2(String value, String color) {
        this.setValue(value);
        this.setColor(color);
    }

}
