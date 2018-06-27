package com.tom.quartz.repository;

import com.tom.quartz.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Mr Tom
 * @date 2018/6/25
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/25
 */
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {

    @Query(value = "SELECT * FROM schedule_job WHERE enable = TRUE", nativeQuery = true)
    List<ScheduleJob> getAllEnableJob();
}
