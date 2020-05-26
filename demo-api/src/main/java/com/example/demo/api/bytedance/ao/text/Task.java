package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class Task implements Serializable {

    private static final long serialVersionUID = -6449251347845073879L;

    /**
     * 检测的文本内容.
     */
    private String            content;

    /**
     * 检测的图片链接.
     */
    private String            image;

    /**
     * 图片数据的 base64 格式，有 image 字段时，此字段无效.
     */
    @JSONField(name = "image_data")
    private String            imageData;

    public Task(String content) {
        this.setContent(content);
    }

    public Task(String image, String imageData) {
        this.setImage(image);
        this.setImageData(imageData);
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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
