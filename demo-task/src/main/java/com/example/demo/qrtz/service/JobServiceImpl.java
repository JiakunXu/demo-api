package com.example.demo.qrtz.service;

import com.example.demo.qrtz.api.JobService;
import com.example.demo.qrtz.api.bo.Job;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    private Class<? extends org.quartz.Job> getJobClass(String className) {
        try {
            return (Class<? extends org.quartz.Job>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private CronScheduleBuilder withMisfireHandlingInstruction(CronScheduleBuilder schedBuilder,
                                                               int misfireInstr) {
        if (misfireInstr == -1) {
            return schedBuilder.withMisfireHandlingInstructionIgnoreMisfires();
        } else if (misfireInstr == 1) {
            return schedBuilder.withMisfireHandlingInstructionFireAndProceed();
        } else if (misfireInstr == 2) {
            return schedBuilder.withMisfireHandlingInstructionDoNothing();
        }

        return schedBuilder;
    }

    @Override
    public Date scheduleJob(Job job) {
        String name = job.getName();
        String group = job.getGroup();

        try {
            if (scheduler.checkExists(JobKey.jobKey(name, group))) {
                throw new RuntimeException("【" + name + " & " + group + "】已存在");
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        JobDetail jobDetail = JobBuilder.newJob(getJobClass(job.getClassName()))
            .withIdentity(JobKey.jobKey(name, group)).withDescription(job.getDescription())
            .requestRecovery(job.isShouldRecover()).storeDurably(job.isDurability()).build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(
                TriggerKey.triggerKey(StringUtils.replace(name, "Task", "Trigger"), group))
            .withDescription(job.getTrigger().getDescription())
            .startAt(job.getTrigger().getStartTime() == null ? new Date()
                : job.getTrigger().getStartTime())
            .endAt(job.getTrigger().getEndTime())
            .withSchedule(withMisfireHandlingInstruction(
                CronScheduleBuilder.cronSchedule(job.getTrigger().getCronExpression()),
                job.getTrigger().getMisfireInstr()))
            .build();

        try {
            return scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date rescheduleJob(Job job) {
        String name = StringUtils.replace(job.getName(), "Task", "Trigger");
        String group = job.getGroup();

        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);

        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            TriggerBuilder<CronTrigger> triggerBuilder = trigger.getTriggerBuilder();

            if (StringUtils.isNotBlank(job.getTrigger().getDescription())) {
                triggerBuilder.withDescription(job.getTrigger().getDescription());
            }

            if (job.getTrigger().getStartTime() != null) {
                triggerBuilder.startAt(job.getTrigger().getStartTime());
            }

            if (job.getTrigger().getEndTime() != null) {
                triggerBuilder.endAt(job.getTrigger().getEndTime());
            }

            triggerBuilder.withSchedule(withMisfireHandlingInstruction(
                CronScheduleBuilder.cronSchedule(job.getTrigger().getCronExpression()),
                job.getTrigger().getMisfireInstr()));

            CronTrigger newTrigger = triggerBuilder.build();

            return scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteJob(String name, String group) {
        try {
            return scheduler.deleteJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void triggerJob(String name, String group) {
        try {
            scheduler.triggerJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseJob(String name, String group) {
        try {
            scheduler.pauseJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resumeJob(String name, String group) {
        try {
            scheduler.resumeJob(JobKey.jobKey(name, group));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
