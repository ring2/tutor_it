package xyz.ring2.admin.core.service;

import org.springframework.transaction.annotation.Transactional;
import xyz.ring2.admin.core.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ring2.admin.core.entity.vo.PermissionTree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Transactional(rollbackFor = Exception.class)
public interface IPermissionService extends IService<Permission> {

    List<PermissionTree> getMenuTree(Integer id);
}
