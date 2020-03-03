package com.junya.config.mp;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.junya.redis.RedisManager;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @program sweet-dream
 * @description: mybatis-plus配置
 * @author: zhangchao
 * @date: 2019/08/11 08:13
 * @since: 1.0.0
 */
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private RedisManager redisManager;

    /**
     * 分页拦截器
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(-1);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        //多租户设置
        // 创建SQL解析器集合
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 创建租户SQL解析器
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                // 设置当前租户ID，实际情况你可以从cookie、或者缓存中拿都行
                return new StringValue((String) redisManager.get("tenantId"));
            }

            @Override
            public String getTenantIdColumn() {
                // 对应数据库租户ID的列名
                return "TENANT_ID";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 是否需要需要过滤某一张表
//                List<String> tableNameList = Arrays.asList("sys_user");
//                if (tableNameList.contains(tableName)){
//                    return true;
//                }
//                return tableNameList.stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
                return false;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

//    /**
//     * 性能分析插件
//     * 设置 dev test 环境开启
//     * @return
//     */
//    @Bean
//    @Profile({"dev","test"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setFormat(true); //格式化SQL输出
//        performanceInterceptor.setMaxTime(1000); //设置超时时间
//        return performanceInterceptor;
//    }

}
