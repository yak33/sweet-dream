package com.junya.quartz.service;

import com.junya.util.result.ResponseMessage;
import com.junya.quartz.bean.ScheduleJob;

/**
 * Quartz定时任务service
 *
 * @author ZhangChao
 * @date 2019/6/10 13:23
 * @since 1.0.0
 */
public interface QuartzService {
    /**
     * 增加一个任务
     * @param scheduleJob
     */
    ResponseMessage addJob(ScheduleJob scheduleJob);

    /**
     * 获取quartz调度器的计划任务
     * @return
     */
    ResponseMessage getScheduleJobList();
    /**
     * 获取quartz调度器的运行任务
     * @return
     */
    ResponseMessage getScheduleJobRunningList();
    /**
     * 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param cronExpression
     */
    ResponseMessage modifyJobTime(String jobName, String jobGroupName, String cronExpression);

    /**
     * 移除一个任务
     * @param jobName
     * @param jobGroupName
     */
    ResponseMessage removeJob(String jobName, String jobGroupName);

    /**
     * 启动所有定时任务
     */
    ResponseMessage startJobs();

    /**
     * 关闭所有定时任务
     */
    ResponseMessage shutdownJobs();

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroupName
     */
    ResponseMessage pauseJob(String jobName, String jobGroupName);

    /**
     * 暂停全部任务
     */
    ResponseMessage pauseAll();

    /**
     * 恢复任务
     * @param jobName
     * @param jobGroupName
     */
    ResponseMessage resumeJob(String jobName, String jobGroupName);

    /**
     * 恢复所有任务
     */
    ResponseMessage resumeAll();

}
