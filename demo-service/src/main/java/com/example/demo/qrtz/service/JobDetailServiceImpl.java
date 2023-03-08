package com.example.demo.qrtz.service;

import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.JobDetailService;
import com.example.demo.qrtz.api.TriggerService;
import com.example.demo.qrtz.api.bo.JobDetail;
import com.example.demo.qrtz.dao.dataobject.JobDetailDO;
import com.example.demo.qrtz.dao.mapper.JobDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDetailServiceImpl implements JobDetailService {

    private static final Logger logger = LoggerFactory.getLogger(JobDetailServiceImpl.class);

    @Autowired
    private TriggerService      triggerService;

    @Autowired
    private JobDetailMapper     jobDetailMapper;

    @Override
    public int countJobDetail(JobDetail jobDetail) {
        if (jobDetail == null) {
            return 0;
        }

        return count(BeanUtil.copy(jobDetail, JobDetailDO::new));
    }

    @Override
    public List<JobDetail> listJobDetails(JobDetail jobDetail) {
        if (jobDetail == null) {
            return null;
        }

        List<JobDetail> list = BeanUtil.copy(list(BeanUtil.copy(jobDetail, JobDetailDO::new)),
            JobDetail::new);

        if (list == null || list.size() == 0) {
            return null;
        }

        for (JobDetail jd : list) {
            jd.setTrigger(
                triggerService.getTrigger(jd.getSchedName(), jd.getJobName(), jd.getJobGroup()));
        }

        return list;
    }

    @Override
    public JobDetail getJobDetail(String schedName, String jobName, String jobGroup) {
        if (StringUtils.isBlank(schedName) || StringUtils.isBlank(jobName)
            || StringUtils.isBlank(jobGroup)) {
            return null;
        }

        JobDetailDO jobDetailDO = new JobDetailDO();
        jobDetailDO.setSchedName(schedName);
        jobDetailDO.setJobName(jobName);
        jobDetailDO.setJobGroup(jobGroup);

        JobDetail jobDetail = BeanUtil.copy(get(jobDetailDO), JobDetail::new);

        if (jobDetail == null) {
            return null;
        }

        jobDetail.setTrigger(triggerService.getTrigger(jobDetail.getSchedName(),
            jobDetail.getJobName(), jobDetail.getJobGroup()));

        return jobDetail;
    }

    private int count(JobDetailDO jobDetailDO) {
        try {
            return jobDetailMapper.count(jobDetailDO);
        } catch (Exception e) {
            logger.error(jobDetailDO.toString(), e);
        }

        return 0;
    }

    private List<JobDetailDO> list(JobDetailDO jobDetailDO) {
        try {
            return jobDetailMapper.list(jobDetailDO);
        } catch (Exception e) {
            logger.error(jobDetailDO.toString(), e);
        }

        return null;
    }

    private JobDetailDO get(JobDetailDO jobDetailDO) {
        try {
            return jobDetailMapper.get(jobDetailDO);
        } catch (Exception e) {
            logger.error(jobDetailDO.toString(), e);
        }

        return null;
    }

}
