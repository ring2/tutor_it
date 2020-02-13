package xyz.ring2.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :  weiquanquan
 * @date :   2020/2/5 13:19
 * description: mybatis plus 配置类 主要配置一些mp的插件
 **/
@Configuration
public class MPConfiguration {

    /**
     *
     * @return 配置mp分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
