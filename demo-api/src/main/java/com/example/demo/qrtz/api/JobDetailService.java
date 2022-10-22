package com.example.demo.qrtz.api;

import com.example.demo.qrtz.api.bo.JobDetail;

import java.util.List;

public interface JobDetailService {

    int countJobDetail(JobDetail jobDetail);

    List<JobDetail> listJobDetails(JobDetail jobDetail);

    JobDetail getJobDetail(String schedName, String jobName, String jobGroup);

}
