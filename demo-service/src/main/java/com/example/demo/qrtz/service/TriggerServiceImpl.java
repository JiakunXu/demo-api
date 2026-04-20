package com.example.demo.qrtz.service;

import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.CronTriggerService;
import com.example.demo.qrtz.api.TriggerService;
import com.example.demo.qrtz.api.bo.Trigger;
import com.example.demo.qrtz.dao.dataobject.TriggerDO;
import com.example.demo.qrtz.dao.mapper.TriggerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TriggerServiceImpl extends ServiceImpl<TriggerMapper, TriggerDO>
                                implements TriggerService {

    @Autowired
    private CronTriggerService cronTriggerService;

    @Override
    public Trigger getTrigger(String schedName, String jobName, String jobGroup) {
        if (StringUtils.isAnyBlank(schedName, jobName, jobGroup)) {
            return null;
        }

        TriggerDO triggerDO = new TriggerDO();
        triggerDO.setSchedName(schedName);
        triggerDO.setJobName(jobName);
        triggerDO.setJobGroup(jobGroup);

        Trigger trigger = BeanUtil.copy(this.get(triggerDO), Trigger.class);

        if (trigger == null) {
            return null;
        }

        trigger.setCronTrigger(cronTriggerService.getCronTrigger(trigger.getSchedName(),
            trigger.getTriggerName(), trigger.getTriggerGroup()));

        return trigger;
    }

}
