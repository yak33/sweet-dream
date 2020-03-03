package com.junya.service.impl;

import com.junya.dao.GyUserMapper;
import com.junya.model.GyUser;
import com.junya.service.IGyUserService;
import com.junya.util.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表设置 服务实现类
 * </p>
 *
 * @author zhangchao
 * @since 2020-03-02
 */
@Service
public class GyUserServiceImpl extends BasicServiceImpl<GyUserMapper, GyUser> implements IGyUserService {

}
