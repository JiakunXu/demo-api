package com.example.demo.qrtz.web;

import com.example.demo.framework.annotation.Log;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.qrtz.api.JobService;
import com.example.demo.qrtz.api.bo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(value = "/job")
public class JobController extends BaseController {

    @Autowired
    private JobService jobService;

    @Log(module = "job")
    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public ObjectResponse<Date> schedule(HttpServletRequest request, HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        return new ObjectResponse<>(jobService.scheduleJob(job));
    }

    @Log(module = "job")
    @RequestMapping(value = "/reschedule", method = RequestMethod.POST)
    public ObjectResponse<Date> reschedule(HttpServletRequest request,
                                           HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        return new ObjectResponse<>(jobService.rescheduleJob(job));
    }

    @Log(module = "job")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ObjectResponse<Boolean> delete(HttpServletRequest request,
                                          HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        return new ObjectResponse<>(jobService.deleteJob(job.getName(), job.getGroup()));
    }

    @Log(module = "job")
    @RequestMapping(value = "/trigger", method = RequestMethod.POST)
    public void trigger(HttpServletRequest request, HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        jobService.triggerJob(job.getName(), job.getGroup());
    }

    @Log(module = "job")
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    public void pause(HttpServletRequest request, HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        jobService.pauseJob(job.getName(), job.getGroup());
    }

    @Log(module = "job")
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public void resume(HttpServletRequest request, HttpServletResponse response) {
        Job job = this.getParameter(request, Job.class);
        jobService.resumeJob(job.getName(), job.getGroup());
    }

}