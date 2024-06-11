package com.example.demo.openai.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import io.micrometer.common.util.StringUtils;
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

    @JSONField(serialize = false)
    private String            content;

    @JSONField(serialize = false)
    private List<Content>     contents;

    @JSONField(name = "content")
    public Object getData() {
        return StringUtils.isBlank(content) ? contents : content;
    }

    private String role;

    private String name;

    public Message(String content, String role) {
        this.content = content;
        this.role = role;
    }

    public Message(String content, String role, String name) {
        this.content = content;
        this.role = role;
        this.name = name;
    }

    public Message(List<Content> contents, String role) {
        this.contents = contents;
        this.role = role;
    }

    public Message(List<Content> contents, String role, String name) {
        this.contents = contents;
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

        public Content(String type, String text) {
            this.type = type;
            this.text = text;
        }

        public Content(String type, ImageUrl imageUrl) {
            this.type = type;
            this.imageUrl = imageUrl;
        }

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
