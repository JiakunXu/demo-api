package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FileUploadInfo implements Serializable {

    @Serial
    private static final long   serialVersionUID = -1178965263010134042L;

    /**
     * 上传唯一标识
     */
    private String              uploadKey;

    /**
     * Header加签上传信息
     */
    private HeaderSignatureInfo headerSignatureInfo;

    @Getter
    @Setter
    public static class HeaderSignatureInfo implements Serializable {

        @Serial
        private static final long   serialVersionUID = 5962298145679799774L;

        /**
         * 传输地址
         */
        private List<String>        resourceUrls;

        /**
         * 请求头，最大size：20
         */
        private Map<String, String> headers;

    }

}
