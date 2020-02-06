package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.ring2.admin.core.entity.Role;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.entity.vo.UserVo;
import xyz.ring2.admin.core.mapper.UserMapper;
import xyz.ring2.admin.core.service.IRoleService;
import xyz.ring2.admin.core.service.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username){
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        if (ObjectUtil.isNotEmpty(user)){
            List<Role> roles = roleService.selectRolesByUserId(user.getId());
            user.setRoles(roles);
        }else{
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

    @Override
    public Map<String,Object> selUserListWithRoleInfo(Integer pageNo,Integer pageSize,String userName) {
        Map<String,Object> data = new HashMap<>();
        Page<User> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userName)){
            queryWrapper.like("username",userName);
        }
        Page<UserVo> userPage = this.baseMapper.selUserListWithRoleInfo(page, queryWrapper);
        data.put("total",userPage.getTotal());
        data.put("userList",userPage.getRecords());
        return data;
    }

    /**
     *  新增用户
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(User user) {
        String encodePwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        return save(user);
    }
}
