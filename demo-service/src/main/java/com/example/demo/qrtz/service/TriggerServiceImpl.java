package com.example.demo.qrtz.service;

import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.CronTriggerService;
import com.example.demo.qrtz.api.TriggerService;
import com.example.demo.qrtz.api.bo.Trigger;
import com.example.demo.qrtz.dao.dataobject.TriggerDO;
import com.example.demo.qrtz.dao.mapper.TriggerMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggerServiceImpl implements TriggerService {

    private static final Logger logger = LoggerFactory.getLogger(TriggerServiceImpl.class);

    @Autowired
    private CronTriggerService  cronTriggerService;

    @Autowired
    private TriggerMapper       triggerMapper;

    @Override
    public Trigger getTrigger(String schedName, String jobName, String jobGroup) {
        if (StringUtils.isBlank(schedName) || StringUtils.isBlank(jobName)
            || StringUtils.isBlank(jobGroup)) {
            return null;
        }

        TriggerDO triggerDO = new TriggerDO();
        triggerDO.setSchedName(schedName);
        triggerDO.setJobName(jobName);
        triggerDO.setJobGroup(jobGroup);

        Trigger trigger = BeanUtil.copy(get(triggerDO), Trigger::new);

        if (trigger == null) {
            return null;
        }

        trigger.setCronTrigger(cronTriggerService.getCronTrigger(trigger.getSchedName(),
            trigger.getTriggerName(), trigger.getTriggerGroup()));

        return trigger;
    }

    private TriggerDO get(TriggerDO triggerDO) {
        try {
            return triggerMapper.get(triggerDO);
        } catch (Exception e) {
            logger.error(triggerDO.toString(), e);
        }

        return null;
    }

}
