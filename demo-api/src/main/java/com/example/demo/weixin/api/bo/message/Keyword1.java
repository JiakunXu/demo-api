package com.example.demo.weixin.api.bo.message;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Keyword1 extends Value {

    private static final long serialVersionUID = -3139515201876777315L;

    public Keyword1() {
    }

    public Keyword1(String value) {
        this.setValue(value);
    }

    public Keyword1(String value, String color) {
        this.setValue(value);
        this.setColor(color);
    }

}
