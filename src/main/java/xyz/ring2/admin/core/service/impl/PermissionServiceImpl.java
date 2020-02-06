package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xyz.ring2.admin.core.entity.Permission;
import xyz.ring2.admin.core.entity.vo.MenuVo;
import xyz.ring2.admin.core.mapper.PermissionMapper;
import xyz.ring2.admin.core.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public List<MenuVo> getMenuTree(Integer id) {
        List<MenuVo> menuTree = new ArrayList<>();
        List<Permission> list = lambdaQuery().
                eq(Permission::getPid, id).
                eq(Permission::getType,0).list();
        if (list.size() > 0) {
            list.forEach(menu -> {
                MenuVo menuVo = new MenuVo();
                BeanUtil.copyProperties(menu,menuVo);
                menuVo.setChildren(getMenuTree(menu.getId()));
                menuTree.add(menuVo);
            });
        }
        return menuTree;
    }


}
