package com.junya.quartz.jobs;

import com.junya.quartz.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * @author ZHANGCHAO
 */
@Slf4j
@Component
public class QuartzDownloadJob implements BaseJob {

//    private static MsgService msgService;
//    @Autowired
//    public void setMsgService(MsgService msgService) {
//        QuartzDownloadJob.msgService = msgService;
//    }
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            log.info("-----------企业版不需要报文回执-----------");
        } catch (Exception e) {
            log.error("<<<<<<<<< Quartz job failed...", e);
        }
    }

}
