package com.junya.config.mp;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

import static com.baomidou.mybatisplus.generator.config.rules.DateType.ONLY_DATE;

/**
 * @program sweet-dream
 * @description: mybatis-plus代码生成
 * @author: zhangchao
 * @date: 2019/08/11 06:17
 * @since: 1.0.0
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //外层包名
        String packageName = "com";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // String projectPath = "D:/project"; //windows下生成文件的位置
//        String projectPath = "/Users/lixinyu/myspace/project"; //mac下生成文件的位置
        gc.setOutputDir(projectPath + "/src/main/java");

        gc.setFileOverride(true);
        gc.setAuthor("zhangchao"); //生成文件的作者名称
        gc.setOpen(false);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setDateType(ONLY_DATE);
//        gc.setServiceName("%sService");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://115.29.143.113:3306/caoliulu?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("LZM@iloveu9");
//        dsc.setUrl("jdbc:sqlserver://192.168.2.9:1433;DatabaseName=tgc_views");
//        dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        dsc.setUsername("develop");
//        dsc.setPassword("Staeco@2019");

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("junya");
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); //数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude("sys_resource,sys_role,sys_role_resource".split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true); //是否生成实体时，生成字段注解
        strategy.setSuperEntityClass("");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
