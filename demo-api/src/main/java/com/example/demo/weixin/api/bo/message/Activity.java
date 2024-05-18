package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
@ToString
public class Activity extends BaseResult {

    @Serial
    private static final long serialVersionUID = -1592586901866328247L;

    /**
     * 动态消息的 ID.
     */
    @JSONField(name = "activity_id")
    private String            activityId;

    /**
     * activity_id 的过期时间戳。默认24小时后过期.
     */
    @JSONField(name = "expiration_time")
    private Long              expirationTime;

    /**
     * 动态消息修改后的状态
     * 0	未开始
     * 1	已开始
     */
    @JSONField(name = "target_state")
    private Integer           targetState;

    /**
     * 动态消息对应的模板信息.
     */
    @JSONField(name = "template_info")
    private TemplateInfo      templateInfo;

    @Getter
    @Setter
    public static class TemplateInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = -7104780464279026257L;

        @JSONField(name = "parameter_list")
        private List<Parameter>   parameterList;

        @Getter
        @Setter
        public static class Parameter implements Serializable {

            @Serial
            private static final long serialVersionUID = -8739749071365849495L;

            /**
             * 要修改的参数名.
             */
            private String            name;

            /**
             * 修改后的参数值.
             */
            private String            value;

        }

    }

}
