package com.example.demo.test.task;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author JiakunXu
 */
@Component
public class TestTask extends JavaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Override
    public ProcessResult process(JobContext context) {
        logger.error(String.valueOf(System.currentTimeMillis()));
        return new ProcessResult(true, "success");
    }

}
