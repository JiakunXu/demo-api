package com.example.demo.qrtz.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@ToString
public class CronTriggerDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 583556687292411315L;

    private String            schedName;

    private String            triggerName;

    private String            triggerGroup;

    private String            cronExpression;

    private String            timeZoneId;

}
