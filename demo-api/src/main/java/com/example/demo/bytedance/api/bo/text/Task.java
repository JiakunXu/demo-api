package com.example.demo.bytedance.api.bo.text;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
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

}
