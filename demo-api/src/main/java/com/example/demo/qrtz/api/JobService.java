package com.example.demo.qrtz.api;

import com.example.demo.qrtz.api.bo.Job;

import java.util.Date;

public interface JobService {

    Date scheduleJob(Job job);

    Date rescheduleJob(Job job);

    boolean deleteJob(String name, String group);

    void triggerJob(String name, String group);

    void pauseJob(String name, String group);

    void resumeJob(String name, String group);

}
