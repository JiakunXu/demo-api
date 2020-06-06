package com.example.demo.test.task;

import org.springframework.stereotype.Component;

/**
 * @author JiakunXu
 */
@Component
public class TestTask extends JavaProcessor {

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        System.out.println("hello schedulerx2.0");
        return new ProcessResult(true);
    }

}
