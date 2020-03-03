package com.junya.util.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * <p>这个 Mapper 支持 id 泛型</p>
 *
 * @author zhangchao
 * @version 1.0.0
 * @param <T>
 * @date 2019-08-07
 * @since 1.0.0
 */
public interface BasicMapper<T> extends Mapper<T> {

	/**
	 * 插入一条记录
	 *
	 * @param entity 实体对象
	 */
	int insert(T entity);

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 */
	int deleteById(Serializable id);

	/**
	 * 根据 columnMap 条件，删除记录
	 *
	 * @param columnMap 表字段 map 对象
	 */
	int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

	/**
	 * 根据 entity 条件，删除记录
	 *
	 * @param wrapper 实体对象封装操作类（可以为 null）
	 */
	int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

	/**
	 * 删除（根据ID 批量删除）
	 *
	 * @param ids 主键ID列表(不能为 null 以及 empty)
	 */
	int deleteBatchIds(@Param(Constants.COLLECTION) Long... ids);

	/**
	 * 根据 ID 修改
	 *
	 * @param entity 实体对象
	 */
	int updateById(@Param(Constants.ENTITY) T entity);

	/**
	 * 根据 whereEntity 条件，更新记录
	 *
	 * @param entity        实体对象 (set 条件值,可以为 null)
	 * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 */
	int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

	/**
	 * 根据 ID 查询
	 *
	 * @param id 主键ID
	 */
	T selectById(Serializable id);

	/**
	 * 查询（根据 columnMap 条件）
	 *
	 * @param columnMap 表字段 map 对象
	 */
	List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

	/**
	 * 根据 entity 条件，查询一条记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询总记录数
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}