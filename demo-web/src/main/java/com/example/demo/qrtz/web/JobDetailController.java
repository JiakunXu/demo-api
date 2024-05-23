package com.example.demo.qrtz.web;

import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.qrtz.api.JobDetailService;
import com.example.demo.qrtz.api.bo.JobDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system/job/detail")
public class JobDetailController extends BaseController {

    @Autowired
    private JobDetailService jobDetailService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<JobDetail> list(HttpServletRequest request, HttpServletResponse response) {
        JobDetail jobDetail = this.getParameter(request, new JobDetail());

        int count = jobDetailService.countJobDetail(jobDetail);

        if (count == 0) {
            return new ListResponse<>(0, null);
        }

        return new ListResponse<>(count, jobDetailService.listJobDetails(jobDetail));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<JobDetail> get(HttpServletRequest request, HttpServletResponse response) {
        String schedName = this.getParameter(request, "schedName");
        String jobName = this.getParameter(request, "jobName");
        String jobGroup = this.getParameter(request, "jobGroup");
        return new ObjectResponse<>(jobDetailService.getJobDetail(schedName, jobName, jobGroup));
    }

}
