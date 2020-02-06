package xyz.ring2.admin.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import xyz.ring2.admin.core.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.ring2.admin.core.entity.vo.UserVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
public interface UserMapper extends BaseMapper<User> {

    Page<UserVo> selUserListWithRoleInfo(Page<User> page, @Param(Constants.WRAPPER)Wrapper<User> wrapper);

}
