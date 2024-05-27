package com.example.demo.qrtz.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@ToString
public class TriggerDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 3845811002495780733L;

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

}
