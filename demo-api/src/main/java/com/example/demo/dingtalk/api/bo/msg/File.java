package com.example.demo.dingtalk.api.bo.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件消息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class File implements Serializable {

    private static final long serialVersionUID = -2414661995045144021L;

    /**
     * 媒体文件id。引用的媒体文件最大10MB。可以通过媒体文件接口上传图片获取。.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

}
