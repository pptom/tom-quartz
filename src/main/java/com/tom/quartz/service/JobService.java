package com.tom.quartz.service;



import com.tom.quartz.entity.ScheduleJob;
import com.tom.quartz.repository.ScheduleJobRepository;
import com.tom.quartz.util.IdGenerateFactory;
import com.tom.quartz.util.ScheduleUtil;
import org.hibernate.service.spi.ServiceException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Mr Tom
 * @date 2018/6/25
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/25
 */
@Service
public class JobService {
    private static Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private ScheduleJobRepository scheduleJobRepository;

    @Autowired
    private Scheduler scheduler;

    public List<ScheduleJob> getAllEnableJob() {
        return scheduleJobRepository.getAllEnableJob();
    }

    public List<ScheduleJob> getAllJob() {
        return scheduleJobRepository.findAll();
    }

    public ScheduleJob findById(Long jobId) throws ServiceException {
        return scheduleJobRepository.findById(jobId)
                .orElseThrow(() -> new ServiceException("ScheduleJob:" + jobId + " not found"));
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public ScheduleJob update(Long jobId, ScheduleJob scheduleJob) throws ServiceException {
        scheduleJob.setLastUpdateTime(new Date());
        ScheduleJob save = scheduleJobRepository.save(scheduleJob);
        if (save.getId() == null) {
            throw new ServiceException("Update product:" + jobId + "failed");
        }
        ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
        return scheduleJob;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(ScheduleJob scheduleJob) throws ServiceException {
        Long id = IdGenerateFactory.nextId();
        scheduleJob.setId(id);
        //设置创建时间
        scheduleJob.setCreateTime(new Date());
        //设置最后更新时间
        scheduleJob.setLastUpdateTime(new Date());
        //若pause没有值，则默认是启动中
        if (scheduleJob.getPause() == null) {
            scheduleJob.setPause(false);
        }
        ScheduleJob newJob = scheduleJobRepository.save(scheduleJob);
        if (newJob.getId() == null) {
            throw new ServiceException("Add product failed");
        }
        ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
        return true;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = findById(jobId);
        if (jobId == null) {
            throw new ServiceException("Delete product:" + jobId + "failed");
        }
        scheduleJobRepository.deleteById(jobId);
        ScheduleUtil.deleteJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 恢复任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean resume(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = findById(jobId);
        scheduleJob.setPause(false);
        scheduleJob.setLastUpdateTime(new Date());
        //更新数据库记录
        ScheduleJob save = scheduleJobRepository.save(scheduleJob);
        if (save.getId() == null) {
            throw new ServiceException("Resume product:" + jobId + " failed");
        }
        ScheduleUtil.resumeJob(scheduler, save);
        return true;
    }

    /**
     * 暂停任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean pause(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = findById(jobId);
        scheduleJob.setPause(true);
        scheduleJob.setLastUpdateTime(new Date());
        //更新数据库记录
        ScheduleJob save = scheduleJobRepository.save(scheduleJob);
        if (save.getId() == null) {
            throw new ServiceException("Pause product:" + jobId + " failed");
        }
        //暂停任务
        ScheduleUtil.pauseJob(scheduler, save);
        return true;
    }

    /**
     * 运行任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean run(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = findById(jobId);
        ScheduleUtil.run(scheduler, scheduleJob);
        return true;
    }
}
