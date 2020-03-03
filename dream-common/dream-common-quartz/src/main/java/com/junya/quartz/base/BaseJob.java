package com.junya.quartz.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ZHANGCHAO
 * @date 2019/11/11 16:55
 * @since 1.0.0
 */
public interface BaseJob extends Job {
    void execute(JobExecutionContext context) throws JobExecutionException;
}
