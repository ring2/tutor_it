package xyz.ring2.admin.core.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.ring2.admin.core.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRolesByUserId(@Param("id") Long id);
}
