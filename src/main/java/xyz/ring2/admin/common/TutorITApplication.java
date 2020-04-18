package xyz.ring2.admin.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author :     weiquanquan
 * @date :       2020/2/13 10:14
 * description:  自定义注解，集中配置启动类上的注解
 **/
@SpringBootApplication
@MapperScan("xyz.ring2.admin.core.mapper") //mp注解 扫描mapper接口
@EnableTransactionManagement // 开启事务管理
@Inherited // 注解可被继承，使用该注解的类继承已有全部注解
@Retention(RetentionPolicy.RUNTIME) // 声明为运行时注解，可在运行时通过反射读取到
@EnableAspectJAutoProxy
public @interface TutorITApplication {
}
