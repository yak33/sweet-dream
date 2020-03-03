package com.junya.quartz.service;

import com.junya.quartz.base.BaseJob;
import com.junya.quartz.bean.ScheduleJob;
import com.junya.quartz.manage.QuartzTaskManager;
import com.junya.util.result.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Quartz定时任务Impl
 *
 * @author ZhangChao
 * @date 2019/6/10 13:24
 * @since 1.0.0
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private QuartzTaskManager quartzTaskManager;

    /**
     * 增加任务
     * @param scheduleJob
     * @return
     */
    @Override
    public ResponseMessage addJob(ScheduleJob scheduleJob) {
        String allClass = "com.junya.quartz.jobs."+scheduleJob.getClazz();
        String result = null;
        try {
            result = quartzTaskManager.addJob(scheduleJob, getClass(allClass).getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseMessage(true, result);
    }

    /**
     * 获取quartz调度器的计划任务
     * @return
     */
    @Override
    public ResponseMessage getScheduleJobList() {
        List<Map<String, String>> lsit = quartzTaskManager.getScheduleJobList();
        return new ResponseMessage(true, lsit);
    }

    /**
     * 获取quartz调度器的运行任务
     * @return
     */
    @Override
    public ResponseMessage getScheduleJobRunningList() {
        List<Map<String, String>> lsit = quartzTaskManager.getScheduleJobRunningList();
        return new ResponseMessage(true, lsit);
    }

    /**
     * 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param cronExpression
     * @return
     */
    @Override
    public ResponseMessage modifyJobTime(String jobName, String jobGroupName, String cronExpression) {
        String result = quartzTaskManager.modifyJobTime(jobName,jobGroupName,cronExpression);
        return new ResponseMessage(true, result);
    }

    /**
     * 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @return
     */
    @Override
    public ResponseMessage removeJob(String jobName, String jobGroupName) {
        String result = quartzTaskManager.removeJob(jobName,jobGroupName);
        return new ResponseMessage(true, result);
    }

    /**
     * 启动所有定时任务
     */
    @Override
    public ResponseMessage startJobs() {
        String result = quartzTaskManager.startJobs();
        return new ResponseMessage(true, result);
    }

    /**
     * 关闭所有定时任务
     */
    @Override
    public ResponseMessage shutdownJobs() {
        String result = quartzTaskManager.shutdownJobs();
        return new ResponseMessage(true, result);
    }

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroupName
     * @return
     */
    @Override
    public ResponseMessage pauseJob(String jobName, String jobGroupName) {
        String result = quartzTaskManager.pauseJob(jobName,jobGroupName);
        return new ResponseMessage(true, result);
    }

    /**
     * 暂停全部任务
     */
    @Override
    public ResponseMessage pauseAll() {
        String result = quartzTaskManager.pauseAll();
        return new ResponseMessage(true, result);
    }

    /**
     * 恢复任务
     * @param jobName
     * @param jobGroupName
     * @return
     */
    @Override
    public ResponseMessage resumeJob(String jobName, String jobGroupName) {
        String result = quartzTaskManager.resumeJob(jobName,jobGroupName);
        return new ResponseMessage(true, result);
    }

    /**
     * 恢复所有任务
     */
    @Override
    public ResponseMessage resumeAll() {
        String result = quartzTaskManager.resumeAll();
        return new ResponseMessage(true, result);
    }

    /**
     * 获取任务调度类
     * @param classname
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }
}
