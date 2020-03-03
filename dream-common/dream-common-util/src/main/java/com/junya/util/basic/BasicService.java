package com.junya.util.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.junya.util.result.ResponseMessage;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义MP的service接口
 *
 * @author ZhangChao
 * @date 2019/8/12 10:07
 * @since 1.0.0
 */
public interface BasicService<T> {

    /**
     * 插入或修改一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    ResponseMessage save(T entity);

    /**
     * 插入或更新（批量）
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default ResponseMessage saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 1000);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    ResponseMessage saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    ResponseMessage removeById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    ResponseMessage removeByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage remove(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param ids 主键ID列表
     */
    ResponseMessage removeByIds(Long... ids);


    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    ResponseMessage update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default ResponseMessage update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    ResponseMessage getById(Serializable id);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    ResponseMessage listByMap(Map<String, Object> columnMap);

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default ResponseMessage getOne(Wrapper<T> queryWrapper) {
        return getOne(queryWrapper, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    ResponseMessage getOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage getMap(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage count(Wrapper<T> queryWrapper);

    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default ResponseMessage count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage list(Wrapper<T> queryWrapper);

    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default ResponseMessage list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage page(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 转换自定义分页的数据的可读性
     *
     * @param pageData
     * @return
     */
    ResponseMessage pageDataConvert(IPage<T> pageData);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default ResponseMessage page(IPage<T> page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage listMaps(Wrapper<T> queryWrapper);

    /**
     * 查询所有列表
     *
     * @see Wrappers#emptyWrapper()
     */
    default ResponseMessage listMaps() {
        return listMaps(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    ResponseMessage pageMaps(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default ResponseMessage pageMaps(IPage<T> page) {
        return pageMaps(page, Wrappers.emptyWrapper());
    }

}
