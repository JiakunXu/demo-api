package com.example.demo.test.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.example.demo.api.shop.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JiakunXu
 */
@Component
public class TestTask extends JavaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Autowired
    private IShopService        openShopService;

    @Override
    public ProcessResult process(JobContext context) {
        return new ProcessResult(true, JSON.toJSONString(openShopService.countShop()));
    }

}
