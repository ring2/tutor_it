package xyz.ring2.admin.core.service;

import org.springframework.transaction.annotation.Transactional;
import xyz.ring2.admin.core.entity.UserRoleRel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ring2
 * @since 2020-02-04
 */
@Transactional(rollbackFor = Exception.class)
public interface IUserRoleRelService extends IService<UserRoleRel> {

}
