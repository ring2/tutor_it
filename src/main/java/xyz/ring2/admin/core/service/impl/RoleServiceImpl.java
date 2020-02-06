package xyz.ring2.admin.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.ring2.admin.core.entity.Role;
import xyz.ring2.admin.core.mapper.RoleMapper;
import xyz.ring2.admin.core.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectRolesByUserId(Long id) {
        return roleMapper.selectRolesByUserId(id);
    }
}
