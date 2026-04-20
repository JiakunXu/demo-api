package com.example.demo.qrtz.service;

import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.JobDetailService;
import com.example.demo.qrtz.api.TriggerService;
import com.example.demo.qrtz.api.bo.JobDetail;
import com.example.demo.qrtz.dao.dataobject.JobDetailDO;
import com.example.demo.qrtz.dao.mapper.JobDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class JobDetailServiceImpl extends ServiceImpl<JobDetailMapper, JobDetailDO>
                                  implements JobDetailService {

    @Autowired
    private TriggerService triggerService;

    @Override
    public int countJobDetail(JobDetail jobDetail) {
        if (jobDetail == null) {
            return 0;
        }

        return this.count(BeanUtil.copy(jobDetail, JobDetailDO.class));
    }

    @Override
    public List<JobDetail> listJobDetails(JobDetail jobDetail) {
        if (jobDetail == null) {
            return null;
        }

        List<JobDetail> list = BeanUtil.copy(this.list(BeanUtil.copy(jobDetail, JobDetailDO.class)),
            JobDetail.class);

        if (CollectionUtils.isEmpty(list)) {
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

        JobDetail jobDetail = BeanUtil.copy(this.get(jobDetailDO), JobDetail.class);

        if (jobDetail == null) {
            return null;
        }

        jobDetail.setTrigger(triggerService.getTrigger(jobDetail.getSchedName(),
            jobDetail.getJobName(), jobDetail.getJobGroup()));

        return jobDetail;
    }

}
