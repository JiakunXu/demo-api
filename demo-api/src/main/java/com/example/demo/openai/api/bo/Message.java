package com.example.demo.openai.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Message implements Serializable {

    private static final long serialVersionUID = -6474093301284154597L;

    private String            role;

    private String            content;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

}
