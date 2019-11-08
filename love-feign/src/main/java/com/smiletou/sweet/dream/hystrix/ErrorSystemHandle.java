package com.smiletou.sweet.dream.hystrix;

import com.smiletou.sweet.dream.entity.SysUser;
import com.smiletou.sweet.dream.result.ResponseMessage;
import com.smiletou.sweet.dream.service.FeignSystemService;
import org.springframework.stereotype.Component;

/**
 * 熔断处理类
 * @author ZHANGCHAO
 * @date 2019/11/6 9:13
 * @since 1.0.0
 */
@Component
public class ErrorSystemHandle implements FeignSystemService {
    /**
     * 根据dictCode获取数据
     *
     * @param dictCode
     * @return
     */
    @Override
    public ResponseMessage getByDictCode(String dictCode) {
        return new ResponseMessage(false,"getByDictCode出现错误",dictCode);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage saveUser(SysUser user) {
        return new ResponseMessage(false,"saveUser出现错误",user);
    }
}
