package com.example.demo.qrtz.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class CronTrigger extends BaseBO {

    @Serial
    private static final long serialVersionUID = 6846400004709729889L;

    private String            schedName;

    private String            triggerName;

    private String            triggerGroup;

    private String            cronExpression;

    private String            timeZoneId;

}