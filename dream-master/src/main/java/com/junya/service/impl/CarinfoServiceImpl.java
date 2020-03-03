package com.junya.service.impl;

import com.junya.dao.CarinfoMapper;
import com.junya.model.Carinfo;
import com.junya.service.ICarinfoService;
import com.junya.util.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangchao
 * @since 2019-08-11
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class CarinfoServiceImpl extends BasicServiceImpl<CarinfoMapper, Carinfo> implements ICarinfoService {

    /**
     * 根据车辆类型查询数据
     *
     * @param typeId
     * @Author ZHANGCHAO
     * @Date 2020/2/26 16:36
     * @retrun com.junya.util.result.ResponseMessage
     **/
    @Override
    public List<Carinfo> getCarinfoByTypeId(String typeId) {
        return basicMapper.getCarinfoByTypeId(typeId);
    }

}
