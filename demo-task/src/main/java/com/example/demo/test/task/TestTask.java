package com.example.demo.test.task;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.springframework.stereotype.Component;

/**
 * @author JiakunXu
 */
@Component
public class TestTask extends JavaProcessor {

    @Override
    public ProcessResult process(JobContext context) {
        return new ProcessResult(true, "success");
    }

}
