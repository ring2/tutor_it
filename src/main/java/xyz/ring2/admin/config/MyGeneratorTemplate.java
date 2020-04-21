package xyz.ring2.admin.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * @author :     ring2
 * @date :       2020/4/19 16:47
 * description:  自定义Mybatis plus generator template
 **/
public class MyGeneratorTemplate {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/tutor-it?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wqq123";

    /**
     * 乐观锁名称
     */
    private static final String OPTIMISTIC_LOCKING = "OptimisticLock";
    /**
     * 逻辑删除名称
     */
    private static final String LOGIC_DELETE = "logicDelete";

    /**
     * 父包名
     */
    private static final String PARENT = "xyz.ring2.admin";

    /**
     * 当前系统路径
     */
    private static final String projectPath = System.getProperty("user.dir");

    /**
     * 模块名称
     */
    private static String moduleName  = "";


    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 设置模块名称
        moduleName = scanner("模块名称:");
        // 采用FreeMarker模板引擎
        FreemarkerTemplateEngine freemarker = new FreemarkerTemplateEngine();
        autoGenerator.setDataSource(dataSourceConfig())
                .setGlobalConfig(globalConfig())
                .setPackageInfo(packageConfig())
                .setTemplateEngine(freemarker)
                .setStrategy(strategyConfig())
                .setCfg(injectionConfig())
                .setTemplate(templateConfig())
                .execute();
    }

    /**
     * @return 数据源配置，通过该配置，指定需要生成代码的具体数据库
     */
    private static DataSourceConfig dataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 以下设置数据库连接信息
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        //设置数据库类型
        dataSourceConfig.setDbType(DbType.MYSQL);
        return dataSourceConfig;
    }

    /**
     * @return 数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
     */
    private static StrategyConfig strategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        // 数据库表映射到实体时下划线转成驼峰规则 exam: a_b  -> aB
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //为lombok模型
        strategyConfig.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        //是否生成实体时，生成字段注解
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        // 设置乐观锁名称
        strategyConfig.setVersionFieldName(OPTIMISTIC_LOCKING);
        // 逻辑删除属性名称
        strategyConfig.setLogicDeleteFieldName(LOGIC_DELETE);
        strategyConfig.setInclude(scanner("表名，若有多个表以英文逗号分割").split(","));
        return strategyConfig;
    }

    /**
     * @return 包名配置，通过该配置，指定生成代码的包路径
     */
    private static PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig();

        packageConfig.setParent(PARENT);
        packageConfig.setModuleName(moduleName);
        return packageConfig;
    }

    /**
     * @return 模板配置，可自定义代码生成的模板，实现个性化操作，具体请查看
     */
    private static TemplateConfig templateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService("/templatesFreeMybatis/service.java");
        templateConfig.setServiceImpl("/templatesFreeMybatis/serviceImpl.java");
        templateConfig.setMapper("/templatesFreeMybatis/mapper.java");
        templateConfig.setController("/templatesFreeMybatis/controller.java");
        templateConfig.setXml("/templatesFreeMybatis/mapper.xml");
        templateConfig.setEntity("/templatesFreeMybatis/entity.java");
        return templateConfig;
    }

    /**
     * @return 全局策略配置
     */
    private static GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("ring2");
        // 主键自动生成
        globalConfig.setIdType(IdType.AUTO);
        globalConfig.setFileOverride(true);
        globalConfig.setOutputDir(projectPath + "/tutor-admin/src/main/java");
        return globalConfig;
    }

    /**
     * @return 注入配置，通过该配置，可注入自定义参数等操作以实现个性化操作
     */
    private static InjectionConfig injectionConfig() {
        String path = "/tutor-admin/src/main/java/"+PARENT.replaceAll("\\.","/")+"/"+moduleName+"/entity/dto";
        System.out.println(path);
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templatesFreeMybatis/entityDTO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + path
                        + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("dto", "xyz.ring2.admin.core.entity.dto");
                this.setMap(map);
            }
        };
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


}
