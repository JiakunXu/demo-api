package com.example.demo.openai.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = -6474093301284154597L;

    private String            content;

    @JSONField(name = "content")
    private List<Content>     contents;

    private String            role;

    private String            name;

    public Message(String content, String role) {
        this.content = content;
        this.role = role;
    }

    public Message(String content, String role, String name) {
        this.content = content;
        this.role = role;
        this.name = name;
    }

    @Getter
    @Setter
    public static class Content implements Serializable {

        @Serial
        private static final long serialVersionUID = 127940070954013659L;

        private String            type;

        private String            text;

        @JSONField(name = "image_url")
        private ImageUrl          imageUrl;

        @Getter
        @Setter
        public static class ImageUrl implements Serializable {

            @Serial
            private static final long serialVersionUID = 1513870905076192344L;

            private String            url;

            private String            detail;

        }

    }

}
