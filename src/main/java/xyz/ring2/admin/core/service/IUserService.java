package xyz.ring2.admin.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import xyz.ring2.admin.core.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Transactional(rollbackFor = Exception.class)
public interface IUserService extends IService<User> {

     User findUserByUsername(String username);
     Map<String,Object> selUserListWithRoleInfo(Integer pageNo,Integer pageSize,String userName);
     boolean saveUser(User user);
}
