package com.junya.service;

import com.junya.model.Carinfo;
import com.junya.util.basic.BasicService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangchao
 * @since 2019-08-11
 */
public interface ICarinfoService extends BasicService<Carinfo> {

    /**
     * 根据车辆类型查询数据
     *
     * @Author ZHANGCHAO
     * @Date 2020/2/26 16:36
     * @param
     * @param typeId
     * @retrun com.junya.util.result.ResponseMessage
     **/
    List<Carinfo> getCarinfoByTypeId(String typeId);

}
