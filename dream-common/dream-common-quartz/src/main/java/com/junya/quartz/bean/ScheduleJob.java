package com.junya.quartz.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * quartz的Javabean
 *
 * @author ZhangChao
 * @date 2019/5/16
 * @since 1.0.0
 */
@Data
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = -6514836923647019517L;

    //任务名
    private String jobName;
    //任务组名
    private String jobGroupName;
    //触发器名
    private String triggerName;
    //触发器组名
    private String triggerGroupName;
    //时间设置 表达式
    private String cron;

    /** 定时任务对应的类（包括包路径），如：cn.imovie.manage.task.job.TicketMoneyLessThanNormalWarn */
    private String clazz;

}
