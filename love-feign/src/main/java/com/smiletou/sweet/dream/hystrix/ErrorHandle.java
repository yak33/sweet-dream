package com.smiletou.sweet.dream.hystrix;

import com.smiletou.sweet.dream.result.ResponseMessage;
import com.smiletou.sweet.dream.service.FeignService;
import org.springframework.stereotype.Component;

/**
 * 熔断处理类
 * @author ZHANGCHAO
 * @date 2019/11/6 9:10
 * @since 1.0.0
 */
@Component
public class ErrorHandle implements FeignService {

    @Override
    public ResponseMessage login(String username, String password, String node, String force) {
        return new ResponseMessage(false,"出现错误！",username+";"+password+";"+node+";"+force);
    }
}
