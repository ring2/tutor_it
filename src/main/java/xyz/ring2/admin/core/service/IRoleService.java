package xyz.ring2.admin.core.service;

import org.apache.ibatis.annotations.Param;
import xyz.ring2.admin.core.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
public interface IRoleService extends IService<Role> {

    List<Role> selectRolesByUserId(Long id);

}
