package com.example.demo.qrtz.api;

import com.example.demo.qrtz.api.bo.Trigger;

public interface TriggerService {

    Trigger getTrigger(String schedName, String jobName, String jobGroup);

}
