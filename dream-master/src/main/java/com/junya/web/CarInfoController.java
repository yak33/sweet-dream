package com.junya.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junya.model.Carinfo;
import com.junya.redis.RedisManager;
import com.junya.service.ICarinfoService;
import com.junya.util.result.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.junya.core.util.ObjectUtil.isNotEmpty;

/**
 * @author ZhangChao
 * @date 2019/7/4 14:53
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/car")
public class CarInfoController {

    @Autowired
    private ICarinfoService carinfoService;
    @Autowired
    private RedisManager redisManager;

    /**
     * 根据车辆型号获取数据
     *
     * @Author ZHANGCHAO
     * @Date 2020/2/26 16:42
     * @param
     * @param typeId
     * @retrun com.junya.util.result.ResponseMessage
     **/
    @GetMapping("/getByTypeId")
    public ResponseMessage getCarinfoByTypeId(@RequestParam(value = "typeId") String typeId){
        List<Carinfo> carinfoList = carinfoService.getCarinfoByTypeId(typeId);
        if (isNotEmpty(carinfoList)){
            Map<String,String> map = new HashMap<>();
            carinfoList.forEach(carinfo -> {
                map.put(carinfo.getCarnum(),carinfo.getGpsnum());
            });
            Boolean b = redisManager.set("typeid=13",map,60);
            if (b){
                log.info("已放入redis");
            } else {
                log.info("无数据，没有放入redis");
            }
            System.out.println(redisManager.get("typeid=13"));
        }
//        ResponseMessage carinfoList = carinfoService.page(new Page<Carinfo>(1,20),new QueryWrapper<Carinfo>().lambda().eq(Carinfo::getTypeid,typeId));
        return new ResponseMessage(true,carinfoList);
    }

    /**
     * 根据类型id获取车辆信息
     *
     * @Author ZHANGCHAO
     * @Date 2020/3/2 11:14
     * @param
     * @param typeId
     * @retrun com.junya.util.result.ResponseMessage
     **/
    @GetMapping("/getCarinfoByTypeId")
    public ResponseMessage getCarInfo(@RequestParam("typeId") String typeId){
        redisManager.set("tenantId","111");
        return carinfoService.list(new QueryWrapper<Carinfo>().lambda().eq(Carinfo::getTypeid,typeId));
    }

    /**
     * 新增车辆信息
     *
     * @Author zhangchao
     * @Date 2020/2/26 22:51
     * @param
     * @param carinfo
     * @retrun com.junya.util.result.ResponseMessage
     **/
    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/save")
    public ResponseMessage saveCarinfo(Carinfo carinfo, HttpServletRequest request){
        ResponseMessage rm = carinfoService.save(carinfo);
        System.out.println(request.getQueryString());
        Carinfo carinfo1 = (Carinfo) rm.getData();
        System.out.println(carinfo1);
        return new ResponseMessage(true,carinfo1);
    }


}
