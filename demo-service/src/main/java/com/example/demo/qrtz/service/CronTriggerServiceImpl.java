package com.example.demo.qrtz.service;

import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.CronTriggerService;
import com.example.demo.qrtz.api.bo.CronTrigger;
import com.example.demo.qrtz.dao.dataobject.CronTriggerDO;
import com.example.demo.qrtz.dao.mapper.CronTriggerMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronTriggerServiceImpl implements CronTriggerService {

    private static final Logger logger = LoggerFactory.getLogger(CronTriggerServiceImpl.class);

    @Autowired
    private CronTriggerMapper   cronTriggerMapper;

    @Override
    public CronTrigger getCronTrigger(String schedName, String triggerName, String triggerGroup) {
        if (StringUtils.isBlank(schedName) || StringUtils.isBlank(triggerName)
            || StringUtils.isBlank(triggerGroup)) {
            return null;
        }

        CronTriggerDO cronTriggerDO = new CronTriggerDO();
        cronTriggerDO.setSchedName(schedName);
        cronTriggerDO.setTriggerName(triggerName);
        cronTriggerDO.setTriggerGroup(triggerGroup);

        return BeanUtil.copy(get(cronTriggerDO), CronTrigger.class);
    }

    private CronTriggerDO get(CronTriggerDO cronTriggerDO) {
        try {
            return cronTriggerMapper.get(cronTriggerDO);
        } catch (Exception e) {
            logger.error(cronTriggerDO.toString(), e);
        }

        return null;
    }

}
