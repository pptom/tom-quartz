package com.tom.quartz.controller;

import com.tom.quartz.entity.ResultEntity;
import com.tom.quartz.entity.ScheduleJob;
import com.tom.quartz.service.JobService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Tom
 * @date 2018/6/25
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/25
 */
@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public String jobIndex(Model model) {
        List<ScheduleJob> scheduleJobList = jobService.getAllJob();
        model.addAttribute("scheduleJobList", scheduleJobList);
        return "job";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Object getJob(@PathVariable("id") Long jobId) throws ServiceException {
        Map<String, Object> map = new HashMap<>();
        ScheduleJob scheduleJob = jobService.findById(jobId);
        map.put("data", scheduleJob);
        if (scheduleJob != null) {
            return ResultEntity.success("查询成功!", map);
        }
        return ResultEntity.fail(500, "查询失败!");
    }

    @PostMapping("/add")
    @ResponseBody
    public Object saveJob(@RequestBody ScheduleJob newScheduleJob) throws ServiceException {
        boolean isSave = jobService.add(newScheduleJob);
        if (isSave) {
            return ResultEntity.success("新增任务成功!");
        }
        return ResultEntity.fail(500, "新增任务失败!");
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public Object updateJob(@PathVariable("id") Long jobId, String cronExpression) throws ServiceException {
        ScheduleJob scheduleJob = jobService.findById(jobId);
        if (scheduleJob != null) {
            scheduleJob.setCronExpression(cronExpression);
        }
        ScheduleJob update = jobService.update(jobId, scheduleJob);
        if (update != null) {
            return ResultEntity.success("任务更新成功!");
        }
        return ResultEntity.fail(500,"任务更新失败!");
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Object deleteJob(@PathVariable("id") Long jobId) throws ServiceException {
        boolean isDelete = jobService.delete(jobId);
        if (isDelete) {
            return ResultEntity.success("任务移除成功!");
        }
        return ResultEntity.fail(500,"任务移除失败!");
    }

    @GetMapping("/trigger/{id}")
    @ResponseBody
    public Object runJob(@PathVariable("id") Long jobId) throws ServiceException {
        boolean isRun = jobService.run(jobId);
        if (isRun) {
            return ResultEntity.success("任务触发成功!");
        }
        return ResultEntity.fail(500, "任务触发异常，请联系管理员!");
    }


    @GetMapping("/pause/{id}")
    @ResponseBody
    public Object pauseJob(@PathVariable("id") Long jobId) throws ServiceException {
        boolean isPause = jobService.pause(jobId);
        if (isPause) {
            return ResultEntity.success("任务暂停成功!");
        }
        return ResultEntity.fail(500, "任务暂停异常，请联系管理员!");
    }

    @GetMapping("/resume/{id}")
    @ResponseBody
    public Object resumeJob(@PathVariable("id") Long jobId) throws ServiceException {
        boolean isResume = jobService.resume(jobId);
        if (isResume) {
            return ResultEntity.success("任务恢复成功!");
        }
        return ResultEntity.fail(500, "任务恢复异常，请联系管理员!");
    }
}
