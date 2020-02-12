package xyz.ring2.admin.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.ring2.admin.core.entity.User;

import java.util.List;

/**
 * @author :     weiquanquan
 * @date :       2020/2/8 14:27
 * description:  从Security获取用户实体
 **/
public class SecurityUserUtil {

    /**
     *
     * @return 用户实体
     */
    public static User getUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * @return 当前用户的权限集
     */
    public static List<GrantedAuthority> getAuth(){
        return (List)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
