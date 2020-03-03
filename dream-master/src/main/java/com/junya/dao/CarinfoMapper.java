package com.junya.dao;

import com.junya.util.basic.BasicMapper;
import com.junya.model.Carinfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangchao
 * @since 2019-08-11
 */
public interface CarinfoMapper extends BasicMapper<Carinfo> {

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
