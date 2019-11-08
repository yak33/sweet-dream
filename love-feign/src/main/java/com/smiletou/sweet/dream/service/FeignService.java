package com.smiletou.sweet.dream.service;

import com.smiletou.sweet.dream.hystrix.ErrorHandle;
import com.smiletou.sweet.dream.result.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign声明式服务调用
 *
 * @author ZHANGCHAO
 * @date 2019/11/5 14:32
 * @since 1.0.0
 */
@FeignClient(value = "ZHZY",fallback = ErrorHandle.class)
public interface FeignService {

    @PostMapping(value = "/core/user/login")
    ResponseMessage login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("node") String node,
                          @RequestParam(value = "force", required = false) String force);
}
