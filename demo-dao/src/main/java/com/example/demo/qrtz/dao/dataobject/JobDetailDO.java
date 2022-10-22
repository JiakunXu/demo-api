package com.example.demo.qrtz.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobDetailDO extends BaseDO {

    private static final long serialVersionUID = 4531504668767263732L;

    private String            schedName;

    private String            jobName;

    private String            jobGroup;

    private String            description;

    private String            jobClassName;

    private String            isDurable;

    private String            isNonconcurrent;

    private String            isUpdateData;

    private String            requestsRecovery;

    private String            jobData;

}
