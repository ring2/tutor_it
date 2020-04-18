package xyz.ring2.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ring2.admin.core.entity.RolePermissionRel;
import xyz.ring2.admin.core.mapper.RolePermissionRelMapper;
import xyz.ring2.admin.core.service.IRolePermissionRelService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Service
public class RolePermissionRelServiceImpl extends ServiceImpl<RolePermissionRelMapper, RolePermissionRel> implements IRolePermissionRelService {

    @Override
    public boolean delRelByRoleAndPerId(Integer perId, Long roleId) {
        return lambdaUpdate().eq(RolePermissionRel::getRoleId, roleId).eq(RolePermissionRel::getPermissionId, perId).remove();
    }
}
