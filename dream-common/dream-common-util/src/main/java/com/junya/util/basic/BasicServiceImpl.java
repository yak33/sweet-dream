package com.junya.util.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.junya.util.exception.GlobalErrorCodeEnum;
import com.junya.util.exception.GlobalException;
import com.junya.util.result.ResponseData;
import com.junya.util.result.ResponseMessage;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义MP的serviceImpl
 *
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 *
 * @author ZhangChao
 * @date 2019/8/12 15:34
 * @since 1.0.0
 */
@SuppressWarnings("ALL")
@Service
public abstract class BasicServiceImpl<M extends BasicMapper<T>, T> implements BasicService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BasicServiceImpl.class);
    private Log log = LogFactory.getLog(getClass());

    @Autowired
    protected M basicMapper;

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 插入或更新
     *
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Override
    public ResponseMessage save(T entity) {
        try {
            if (null != entity) {
                Class<?> cls = entity.getClass();
                TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
                Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
                String keyProperty = tableInfo.getKeyProperty();
                Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))){
                    return new ResponseMessage(retBool(basicMapper.insert(entity)), entity);
                } else {
                    return new ResponseMessage(retBool(basicMapper.updateById(entity)), entity);
                }
            }
            return new ResponseMessage(false);
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    /**
     * 批量插入或更新
     *
     * @param entityList ignore
     * @param batchSize ignore
     * @return ignore
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseMessage saveBatch(Collection<T> entityList, int batchSize) {
        try {
            Assert.notEmpty(entityList, "error: entityList must not be empty");
            Class<?> cls = currentModelClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            try (SqlSession batchSqlSession = sqlSessionBatch()) {
                int i = 0;
                for (T entity : entityList) {
                    Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
                    if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                        batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                    } else {
                        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                        param.put(Constants.ENTITY, entity);
                        batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                    }
                    // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            }
            return new ResponseMessage(true);
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage removeById(Serializable id) {
        try {
            if (id == null) {
                return new ResponseMessage(false, "10004");
            }
            return new ResponseMessage(true, SqlHelper.retBool(basicMapper.deleteById(id)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage removeByMap(Map<String, Object> columnMap) {
        try {
            if (columnMap == null) {
                return new ResponseMessage(false, "10004");
            }
            return new ResponseMessage(true, SqlHelper.retBool(basicMapper.deleteByMap(columnMap)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage remove(Wrapper<T> wrapper) {
        try {
            return new ResponseMessage(true, SqlHelper.retBool(basicMapper.delete(wrapper)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage removeByIds(Long... ids) {
        try {
            return new ResponseMessage(true, SqlHelper.retBool(basicMapper.deleteBatchIds(ids)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage update(T entity, Wrapper<T> updateWrapper) {
        try {
            return new ResponseMessage(true, retBool(basicMapper.update(entity, updateWrapper)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage getById(Serializable id) {
        try {
            if (id == null) {
                return new ResponseMessage(false, "404");
            }
            T entity = basicMapper.selectById(id);

            return new ResponseMessage(true, entity);
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage listByMap(Map<String, Object> columnMap) {
        try {
            return new ResponseMessage(true, basicMapper.selectByMap(columnMap));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        try {
            if (throwEx) {
                return new ResponseMessage(true, basicMapper.selectOne(queryWrapper));
            }
            return new ResponseMessage(true, SqlHelper.getObject(log, basicMapper.selectList(queryWrapper)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage getMap(Wrapper<T> queryWrapper) {
        try {
            return new ResponseMessage(true, SqlHelper.getObject(log, basicMapper.selectMaps(queryWrapper)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage count(Wrapper<T> queryWrapper) {
        try {
            return new ResponseMessage(true, SqlHelper.retCount(basicMapper.selectCount(queryWrapper)));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage list(Wrapper<T> queryWrapper) {
        try {
            return new ResponseMessage(true, basicMapper.selectList(queryWrapper));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage page(IPage<T> page, Wrapper<T> queryWrapper) {
        try {
            ResponseData<T> responseData = new ResponseData<>();
            IPage<T> pageResult = basicMapper.selectPage(page, queryWrapper);
            Long pageNum = pageResult.getCurrent();
            Long pageSize = pageResult.getSize();
            Long totalPage = pageResult.getPages();
            Long totalRow = pageResult.getTotal();
            responseData.setTotalPage(totalPage.intValue());
            responseData.setPageNumber(pageNum.intValue());
            responseData.setPageSize(pageSize.intValue());
            responseData.setTotalRow(totalRow.intValue());
            responseData.setList(pageResult.getRecords());
            return new ResponseMessage(true, responseData);
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    /**
     * 转换自定义分页的数据的可读性
     *
     * @param pageData
     * @return
     */
    @Override
    public ResponseMessage pageDataConvert(IPage<T> pageData) {
        if (null == pageData){
            return null;
        }
        ResponseData<T> responseData = new ResponseData<>();
        Long pageNum = pageData.getCurrent();
        Long pageSize = pageData.getSize();
        Long totalPage = pageData.getPages();
        Long totalRow = pageData.getTotal();
        responseData.setTotalPage(totalPage.intValue());
        responseData.setPageNumber(pageNum.intValue());
        responseData.setPageSize(pageSize.intValue());
        responseData.setTotalRow(totalRow.intValue());
        responseData.setList(pageData.getRecords());
        return new ResponseMessage(true, responseData);

    }

    @Override
    public ResponseMessage listMaps(Wrapper<T> queryWrapper) {
        try {
            return new ResponseMessage(true, basicMapper.selectMaps(queryWrapper));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    @Override
    public ResponseMessage pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        try {
            return new ResponseMessage(true, basicMapper.selectMapsPage(page, queryWrapper));
        }catch(Exception e){
            throw new GlobalException(GlobalErrorCodeEnum.DB_ERROR,e.getMessage());
        }
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        if (Pattern.matches("[A-Z]",str.substring(0,1))){
            return str;
        }
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获得总页数
     *
     * @param totalCount 总记录数
     * @param maxResults  每页多少条
     * @return
     */
    protected int getTotal(int totalCount, int maxResults) {
        return totalCount % maxResults == 0 ? totalCount / maxResults : totalCount / maxResults + 1;
    }

}
