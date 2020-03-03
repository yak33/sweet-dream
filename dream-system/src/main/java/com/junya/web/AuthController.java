package com.junya.web;


import com.junya.model.GyUser;
import com.junya.service.IGyUserService;
import com.junya.util.result.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表设置 前端控制器
 * </p>
 *
 * @author zhangchao
 * @since 2020-03-02
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private IGyUserService userService;

    /**
     * 新增用户
     *
     * @Author ZHANGCHAO
     * @Date 2020/3/3 10:58
     * @param
     * @param user
     * @retrun com.junya.util.result.ResponseMessage
     **/
    @PostMapping("/addUser")
    public ResponseMessage saveUser(GyUser user){
        System.out.println(user);
        return userService.save(user);
    }

}
