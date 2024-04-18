package com.example.demo.weixin.api.bo.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Data implements Serializable {

    private static final long serialVersionUID = 6650690114761764500L;

    private Keyword1          keyword1;

    private Keyword2          keyword2;

    private Keyword3          keyword3;

    @Getter
    @Setter
    public static class Keyword1 extends Value {

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

    @Getter
    @Setter
    public static class Keyword2 extends Value {

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

    @Getter
    @Setter
    public static class Keyword3 extends Value {

        private static final long serialVersionUID = -3139515201876777315L;

        public Keyword3() {
        }

        public Keyword3(String value) {
            this.setValue(value);
        }

        public Keyword3(String value, String color) {
            this.setValue(value);
            this.setColor(color);
        }

    }

}
