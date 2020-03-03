package com.junya.config.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 元对象处理 -- 填充器
 * 自动填充公共字段
 *
 * @author ZhangChao
 * @date 2019/9/24 11:11
 * @since 1.0.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        // 获取到需要被填充的字段值
        Object createBy = getFieldValByName("createBy", metaObject);
        Object createDate = getFieldValByName("createDate", metaObject);
        // 如果不手动设置，就自动填充默认值
        if (createBy == null){
            this.strictInsertFill(metaObject, "createBy", String.class, "-admin-");
        }
        // 如果不手动设置，就自动填充默认值
        if (createDate == null){
            this.strictInsertFill(metaObject,"createDate", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        // 获取到需要被填充的字段值
        Object updateBy = getFieldValByName("updateBy", metaObject);
        Object updateDate = getFieldValByName("updateDate", metaObject);
        // 如果不手动设置，就自动填充默认值
        if (updateBy == null){
            this.strictUpdateFill(metaObject, "updateBy", String.class,"-admin-");
        }
        // 如果不手动设置，就自动填充默认值
        if (updateDate == null){
            this.strictUpdateFill(metaObject,"updateDate", Date.class, new Date());
        }
    }
}
