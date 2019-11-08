package com.smiletou.sweet.dream.service;

import com.smiletou.sweet.dream.entity.SysUser;
import com.smiletou.sweet.dream.hystrix.ErrorSystemHandle;
import com.smiletou.sweet.dream.result.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign声明式服务调用
 *
 * @author ZHANGCHAO
 * @date 2019/11/5 14:32
 * @since 1.0.0
 */
@FeignClient(value = "enterprise-springcloud-system",fallback = ErrorSystemHandle.class)
public interface FeignSystemService {

    /**
     * 根据dictCode获取数据
     * @param dictCode
     * @return
     */
    @GetMapping("/sys/dict/getByDictCode")
    ResponseMessage getByDictCode(@RequestParam(value = "dictCode") String dictCode);

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/sys/user/saveUser")
    ResponseMessage saveUser(@RequestBody SysUser user);

}
