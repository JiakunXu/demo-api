package com.example.demo.qrtz.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class JobDetail extends BaseBO {

    @Serial
    private static final long serialVersionUID = -4405264333873271403L;

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

    private Trigger           trigger;

}
