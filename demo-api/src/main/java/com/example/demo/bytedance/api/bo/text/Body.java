package com.example.demo.bytedance.api.bo.text;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Body implements Serializable {

    @Serial
    private static final long serialVersionUID = -4773047490509322993L;

    @JSONField(name = "targets")
    private List<String>      targetList;

    /**
     * 检测任务列表.
     */
    @JSONField(name = "tasks")
    private List<Task>        taskList;

    @Getter
    @Setter
    public static class Task implements Serializable {

        @Serial
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

}
