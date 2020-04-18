package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import xyz.ring2.admin.core.entity.Permission;
import xyz.ring2.admin.core.entity.Role;
import xyz.ring2.admin.core.entity.RolePermissionRel;
import xyz.ring2.admin.core.entity.UserRoleRel;
import xyz.ring2.admin.core.entity.vo.PermissionTree;
import xyz.ring2.admin.core.entity.vo.RoleTree;
import xyz.ring2.admin.core.mapper.PermissionMapper;
import xyz.ring2.admin.core.mapper.RoleMapper;
import xyz.ring2.admin.core.service.IRolePermissionRelService;
import xyz.ring2.admin.core.service.IRoleService;
import xyz.ring2.admin.core.service.IUserRoleRelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Autowired
    IUserRoleRelService userRoleRelService;

    @Autowired
    IRolePermissionRelService relService;

    @Override
    public List<Role> selectRolesByUserId(Long id) {
        return roleMapper.selectRolesByUserId(id);
    }

    @Override
    public List<RoleTree> selectRoleTreeWithPermission() {
        List<RoleTree> roleTreeList = new ArrayList<>();
        for (Role role : lambdaQuery().list()) {
            RoleTree roleTree = new RoleTree();
            BeanUtil.copyProperties(role, roleTree);
            List<PermissionTree> menuTreeByRoleId = getMenuTreeByRoleId(role.getId(), 0);
            roleTree.setChildren(menuTreeByRoleId);
            roleTreeList.add(roleTree);
        }
        return roleTreeList;
    }

    /**
     * 根据角色id删除角色,同时删除用户角色、角色权限关联表信息
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeRole(Long id) {
        //先删角色
        boolean remove = lambdaUpdate().eq(Role::getId, id).remove();
        //再删用户角色关联
        userRoleRelService.lambdaUpdate().eq(UserRoleRel::getRoleId, id).remove();
        //删角色权限关联
        relService.lambdaUpdate().eq(RolePermissionRel::getRoleId, id).remove();
        if (remove) {
            return true;
        }
        //如何角色删除失败则进行编程式事务回滚另外这三个操作
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return false;
    }

    /**
     * 构造角色权限关联对象集合并进行批量保存
     * @param id
     * @param permissionIds
     * @return
     */
    @Override
    public boolean saveRolePermission(Integer id, List<Integer> permissionIds) {
        List<RolePermissionRel> list = new ArrayList<>();
        permissionIds.forEach(p -> {
            RolePermissionRel rolePermissionRel = new RolePermissionRel();
            rolePermissionRel.setRoleId(id).setPermissionId(p);
            list.add(rolePermissionRel);
        });
        return relService.saveBatch(list);
    }

    /**
     * 根据角色id获取其用户的权限信息
     * 构建成Tree型结构返回
     *
     * @param id
     * @return
     */
    private List<PermissionTree> getMenuTreeByRoleId(Long roleId, Integer id) {
        List<Long> list1 = new ArrayList<Long>() {{
            add(roleId);
        }};
        List<Permission> list = permissionMapper.getPermissionTree(list1, id);
        List<PermissionTree> menuTree = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(menu -> {
                PermissionTree permissionTree = new PermissionTree();
                BeanUtil.copyProperties(menu, permissionTree);
                permissionTree.setChildren(getMenuTreeByRoleId(list1.get(0), menu.getId()));
                menuTree.add(permissionTree);
            });
        }
        return menuTree;
    }
}
