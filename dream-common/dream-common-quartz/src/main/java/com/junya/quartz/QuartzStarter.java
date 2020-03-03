package com.junya.quartz;

import com.junya.quartz.service.QuartzService;
import com.junya.util.result.ResponseMessage;
import com.junya.quartz.bean.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Quartz启动器
 * @author ZhangChao
 * @date 2019/5/15
 * @since 1.0.0
 */
@Slf4j
@Component
public class QuartzStarter implements CommandLineRunner {

    @Autowired
    private QuartzService quartzService;

    /**
     * 开始执行任务
     **/
    @Override
    public void run(String... args) throws Exception {
        // 下载报文任务
        downloadJob();

        // 获取所有的任务
        getScheduleJobList();
    }

    /**
     * 下载报文任务
     */
    private void downloadJob(){
        //增加下载任务
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("downloadJob");
        scheduleJob.setJobGroupName("downloadJobGroup");
        scheduleJob.setTriggerName("downloadTrig");
        scheduleJob.setTriggerGroupName("downloadTriggerGroup");
        scheduleJob.setCron("0 0/1 * * * ?");
        scheduleJob.setClazz("QuartzDownloadJob");
        quartzService.addJob(scheduleJob);
    }

    /**
     * 获取所有任务
     */
    private void getScheduleJobList(){
        ResponseMessage rm = quartzService.getScheduleJobList();
        List<Map<String, String>> list = (List<Map<String, String>>) rm.getData();
        log.info("quartz >>>>>>> 任务列表中有共"+list.size()+"个任务 <<<<<<<");
        list.forEach(System.out::println);
    }

}
