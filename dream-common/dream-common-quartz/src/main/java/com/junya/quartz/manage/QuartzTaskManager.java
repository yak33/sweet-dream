package com.junya.quartz.manage;

import com.junya.quartz.bean.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * 功能:封装了 Quartz 动态添加、修改和删除定时任务时间的方法
 * 
 * @author ZJ
 * @date 2019-05-13
 */
@Configuration
public class QuartzTaskManager {

	private static final Logger logger = LoggerFactory.getLogger(QuartzTaskManager.class);

	private static Scheduler scheduler;

	static {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能： 添加一个定时任务
	 * 
	 * @param jobClass           任务的类类型 eg:TimedMassJob.class
	 * @param objects            可变参数需要进行传参的值
	 */
	public String addJob(ScheduleJob scheduleJob, Class<? extends Job> jobClass, Object... objects) {
		try {
			// 任务名，任务组，任务执行类
			//定义一个JobDetail
			JobDetail jobDetail = JobBuilder.newJob(jobClass)
					//定义name和group
					.withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroupName())
					//job需要传递的内容
//					.usingJobData("name", "hello")
					.build();
			// 触发器
			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					// 该数据可以通过Job中的JobDataMap dataMap =
					// context.getJobDetail().getJobDataMap();来进行参数传递值
					jobDetail.getJobDataMap().put("data" + (i + 1), objects[i]);
				}
			}
			//定义一个Trigger
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
					// 触发器名,触发器组
					.withIdentity(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroupName())
					//加入scheduler之后立刻执行
					.startNow();
			// 触发器时间设定
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCron()));
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			// 调度容器设置JobDetail和Trigger
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
			return "增加任务成功";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取quartz调度器的计划任务
	 */
	public List<Map<String, String>> getScheduleJobList() {
		List<Map<String, String>> jobList = new ArrayList<>();
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
//					ScheduleJob job = new ScheduleJob();
					Map<String, String> map = new HashMap<>();
					map.put("jobName",jobKey.getName());
					map.put("jobGroupName",jobKey.getGroup());
					map.put("clazz",jobKey.getClass().toString());
					map.put("jobDesc","触发器:"+trigger.getKey()); //任务描述
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					map.put("jobStatus",triggerState.name()); //任务状态
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						map.put("cronExpression",cronExpression); //任务运行时间表达式
					}else if(trigger instanceof SimpleTrigger){
						SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
						long milliseconds = simpleTrigger.getRepeatInterval();
						map.put("timeValue", String.valueOf((milliseconds/1000))); //时间值
					}
					jobList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}

	/**
	 * 获取quartz调度器的运行任务
	 * @return
	 */
	public List<Map<String, String>> getScheduleJobRunningList(){
		List<Map<String, String>> jobList = new ArrayList<>();
		try {
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			jobList = new ArrayList<>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
//				ScheduleJob job = new ScheduleJob();
				Map<String, String> map = new HashMap<>();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				map.put("jobName",jobKey.getName());
				map.put("jobGroupName",jobKey.getGroup());
				map.put("clazz",jobKey.getClass().toString());
				map.put("jobDesc","触发器:"+trigger.getKey()); //任务描述
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				map.put("jobStatus",triggerState.name()); //任务状态
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					map.put("cronExpression",cronExpression); //任务运行时间表达式
				}else if(trigger instanceof SimpleTrigger){
					SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
					long milliseconds = simpleTrigger.getRepeatInterval();
					map.put("timeValue", String.valueOf((milliseconds/1000))); //时间值
				}
				jobList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}


	/**
	 * 功能：修改一个任务的触发时间
	 * 
	 */
	public String modifyJobTime(String jobName, String jobGroupName, String cronExpression) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return null;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cronExpression)) {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
//				// 触发器
//				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
//				// 触发器名,触发器组
//				triggerBuilder.withIdentity(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroupName());
//				triggerBuilder.startNow();
//				// 触发器时间设定
//				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCron()));
//				// 创建Trigger对象
//				trigger = (CronTrigger) triggerBuilder.build();

				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

				// 方式一 ：修改一个任务的触发时间
				scheduler.rescheduleJob(triggerKey, trigger);
			}
			return "修改成功！";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 功能: 移除一个任务
	 * 
	 */
	public String removeJob(String jobName, String jobGroupName) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
			// 停止触发器
			scheduler.pauseTrigger(triggerKey);
			// 移除触发器
			scheduler.unscheduleJob(triggerKey);
			// 删除任务
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
			return "移除任务："+jobName+"成功";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 功能：启动所有定时任务
	 */
	public String startJobs() {
		try {
			scheduler.start();
			return "全部任务已启动";
		} catch (Exception e) {
			logger.info("启动全部任务失败!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * 功能：关闭所有定时任务
	 */
	public String shutdownJobs() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
			return "全部任务已关闭";
		} catch (Exception e) {
			logger.info("关闭全部任务失败!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * 暂停任务
	 * @param jobName
	 * @param jobGroupName
	 */
	public String pauseJob(String jobName, String jobGroupName){
		JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
		try {
			scheduler.pauseJob(jobKey);
			return "任务："+jobName+" 已暂停";
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("暂停任务:"+jobName+"失败!");
		}
		return null;
	}

	/**
	 * 暂停全部任务
	 * @throws SchedulerException
	 */
	public String pauseAll(){
		try {
			scheduler.pauseAll();
			return "全部任务已暂停";
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("暂停全部任务失败!");
		}
		return null;
	}

	/**
	 * 恢复任务
	 * @param jobName
	 * @param jobGroupName
	 */
	public String resumeJob(String jobName, String jobGroupName){
		JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
		try {
			scheduler.resumeJob(jobKey);
			return "任务："+jobName+" 已恢复";
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("恢复任务:"+jobGroupName+"失败!");
		}
		return null;
	}


	/**
	 * 恢复所有任务
	 * @throws Exception
	 */
	public String resumeAll() {
		try {
			scheduler.resumeAll();
			return "全部任务已恢复";
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.info("恢复全部任务失败!");
		}
		return null;
	}


	public static void main(String[] args) throws Exception {

//		// 启动scheduler
//		scheduler.start();
//		// 创建HelloworldJob的JobDetail实例，并设置name/group
//		JobDetail jobDetail = JobBuilder.newJob(HelloworldJob.class)
//				.withIdentity("myJob","myJobGroup1")
//				//JobDataMap可以给任务传递参数
//				.usingJobData("job_param","job_param1")
//				.build();
//		// 创建Trigger触发器设置使用cronSchedule方式调度
//		Trigger trigger = TriggerBuilder.newTrigger()
//				.withIdentity("myTrigger","myTriggerGroup1")
//				.usingJobData("job_trigger_param","job_trigger_param1")
//				.startNow()
//				//.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? 2018"))
//				.build();
//		// 注册JobDetail实例到scheduler以及使用对应的Trigger触发时机
//		scheduler.scheduleJob(jobDetail,trigger);
//		// 启动
//		if (!scheduler.isShutdown()) {
//			scheduler.start();
//		}
	}


}
