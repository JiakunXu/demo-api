package com.example.demo.qrtz.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class Trigger extends BaseBO {

    @Serial
    private static final long serialVersionUID = -7793431375433710075L;

    private String            schedName;

    private String            triggerName;

    private String            triggerGroup;

    private String            jobName;

    private String            jobGroup;

    private String            description;

    private Long              nextFireTime;

    private Long              prevFireTime;

    private Integer           priority;

    private String            triggerState;

    private String            triggerType;

    private Long              startTime;

    private Long              endTime;

    private String            calendarName;

    private Integer           misfireInstr;

    private String            jobData;

    private CronTrigger       cronTrigger;

}
