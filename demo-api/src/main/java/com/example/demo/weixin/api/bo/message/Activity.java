package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
@ToString
public class Activity extends BaseResult {

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

}
