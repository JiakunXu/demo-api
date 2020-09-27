package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 消息体.
 * 
 * @author JiakunXu
 */
public class Body implements Serializable {

    private static final long serialVersionUID = -593192103771521711L;

    /**
     * 消息体的标题，建议50个字符以内.
     */
    private String            title;

    /**
     * 消息体的表单，最多显示6个，超过会被隐藏.
     */
    private Form              form;

    /**
     * 单行富文本信息.
     */
    private Rich              rich;

    /**
     * 消息体的内容，最多显示3行.
     */
    private String            content;

    /**
     * 消息体中的图片，支持图片资源@mediaId.
     */
    private String            image;

    /**
     * 自定义的附件数目。此数字仅供显示，钉钉不作验证.
     */
    @JSONField(name = "file_count")
    private String            fileCount;

    /**
     * 自定义的作者名字.
     */
    private String            author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Rich getRich() {
        return rich;
    }

    public void setRich(Rich rich) {
        this.rich = rich;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
