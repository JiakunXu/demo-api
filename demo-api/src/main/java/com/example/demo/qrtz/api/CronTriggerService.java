package com.example.demo.qrtz.api;

import com.example.demo.qrtz.api.bo.CronTrigger;

public interface CronTriggerService {

    CronTrigger getCronTrigger(String schedName, String triggerName, String triggerGroup);

}
