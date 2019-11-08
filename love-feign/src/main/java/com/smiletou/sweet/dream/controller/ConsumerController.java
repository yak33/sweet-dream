package com.smiletou.sweet.dream.controller;

import com.smiletou.sweet.dream.entity.SysUser;
import com.smiletou.sweet.dream.result.ResponseMessage;
import com.smiletou.sweet.dream.service.FeignService;
import com.smiletou.sweet.dream.service.FeignSystemService;
import com.smiletou.sweet.dream.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhangChao
 * @date 2019/10/31 14:44
 * @since 1.0.0
 */
@RestController
public class ConsumerController {

    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private FeignService feignService;
    @Autowired
    private FeignSystemService feignSystemService;

    @RequestMapping(value = "/getAll")
    public ResponseMessage list(@RequestParam(value = "dictCode") String dictCode){
        return ribbonService.getOtherServerData(dictCode);
    }

    @PostMapping("/saveUser")
    public ResponseMessage saveUser(@RequestBody SysUser user){
        return feignSystemService.saveUser(user);
    }

    /**
     * Feign方式调用服务
     *
     * @param username
     * @param password
     * @param node
     * @param force
     * @return
     */
    @PostMapping("/login")
    public ResponseMessage login(String username, String password, String node, String force){
        return feignService.login(username,password,node,force);
    }

    @GetMapping("/getByDictCode")
    ResponseMessage getByDictCode(String dictCode){
        return feignSystemService.getByDictCode(dictCode);
    }


}
