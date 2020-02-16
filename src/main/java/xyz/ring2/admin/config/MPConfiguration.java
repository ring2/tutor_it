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
     * 使用方法： case1：单表分页查询
     *              通过相关实体的 baseMapper.selectPage/selectMapPage 并传入Page对象
     *              及Warpper对象进行查询
     *            case2：多表查询  可以向以下用例的形式进行操作
     *  IPage<UserVo> selUserListWithRoleInfo(Page<User> page, @Param(Constants.WRAPPER)Wrapper<User> wrapper);
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
