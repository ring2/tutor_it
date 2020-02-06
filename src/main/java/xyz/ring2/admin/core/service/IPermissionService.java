package xyz.ring2.admin.core.service;

import xyz.ring2.admin.core.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ring2.admin.core.entity.vo.MenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
public interface IPermissionService extends IService<Permission> {

    List<MenuVo> getMenuTree(Integer id);
}
