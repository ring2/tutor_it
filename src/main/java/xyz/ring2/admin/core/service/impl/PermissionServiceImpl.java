package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ring2.admin.core.entity.Permission;
import xyz.ring2.admin.core.entity.Role;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.entity.vo.PermissionTree;
import xyz.ring2.admin.core.mapper.PermissionMapper;
import xyz.ring2.admin.core.service.IPermissionService;
import xyz.ring2.admin.utils.SecurityUserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    /**
     * 根据当前登录的用户获取其用户的权限信息
     * 构建成Tree型结构返回
     *
     * @param id
     * @return
     */
    @Override
    public List<PermissionTree> getMenuTree(Integer id) {
        User user = SecurityUserUtil.getUser();
        List<Long> ids = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> list = this.baseMapper.getPermissionTree(ids, id);
        List<PermissionTree> permissionTrees = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(permission -> {
                PermissionTree permissionTree = new PermissionTree();
                BeanUtil.copyProperties(permission, permissionTree);
                permissionTree.setChildren(getMenuTree(permission.getId()));
                permissionTrees.add(permissionTree);
            });
        }
        return permissionTrees;
    }


}
