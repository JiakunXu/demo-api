package com.example.demo.qrtz.service;

import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.qrtz.api.CronTriggerService;
import com.example.demo.qrtz.api.bo.CronTrigger;
import com.example.demo.qrtz.dao.dataobject.CronTriggerDO;
import com.example.demo.qrtz.dao.mapper.CronTriggerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CronTriggerServiceImpl extends ServiceImpl<CronTriggerMapper, CronTriggerDO>
                                    implements CronTriggerService {

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

        return BeanUtil.copy(this.get(cronTriggerDO), CronTrigger.class);
    }

}
