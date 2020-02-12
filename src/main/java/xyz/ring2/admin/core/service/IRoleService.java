package xyz.ring2.admin.core.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import xyz.ring2.admin.core.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ring2.admin.core.entity.vo.RoleTree;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Transactional(rollbackFor = Exception.class)
public interface IRoleService extends IService<Role> {

    List<Role> selectRolesByUserId(Long id);

    List<RoleTree> selectRoleTreeWithPermission();

    boolean removeRole(Long id);

    boolean saveRolePermission(Integer id,List<Integer> permissionIds);

}
