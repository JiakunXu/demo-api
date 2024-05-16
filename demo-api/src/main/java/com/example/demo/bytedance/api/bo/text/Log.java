package com.example.demo.bytedance.api.bo.text;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Log implements Serializable {

    private static final long serialVersionUID = -2775327067303387248L;

    /**
     * 请求 id.
     */
    @JSONField(name = "log_id")
    private String            logId;

    /**
     * 检测结果列表.
     */
    @JSONField(name = "data")
    private List<Data>        dataList;

    // 当 access_token 检验失败时会返回如下信息

    @JSONField(name = "error_id")
    private String            errorId;

    private String            code;

    private String            message;

    private String            exception;

    @Getter
    @Setter
    public static class Data implements Serializable {

        private static final long serialVersionUID = -6952598677610899377L;

        /**
         * 检测结果-状态码.
         */
        private int               code;

        /**
         * 检测结果- 消息.
         */
        private String            msg;

        /**
         * 检测结果-数据 id.
         */
        @JSONField(name = "data_id")
        private String            dataId;

        /**
         * 检测结果-任务 id.
         */
        @JSONField(name = "task_id")
        private String            taskId;

        /**
         * 检测结果-置信度列表.
         */
        @JSONField(name = "predicts")
        private List<Predict>     predictList;

        @Getter
        @Setter
        public static class Predict implements Serializable {

            private static final long serialVersionUID = -6768050574984180332L;

            /**
             * 检测结果-置信度-服务/目标.
             */
            private String            target;

            /**
             * 检测结果-置信度-模型/标签.
             */
            @JSONField(name = "model_name")
            private String            modelName;

            /**
             * 检测结果-置信度-概率，值为 0 或者 1，当值为 1 时表示检测的文本包含违法违规内容.
             */
            private int               prob;

        }

    }

}
