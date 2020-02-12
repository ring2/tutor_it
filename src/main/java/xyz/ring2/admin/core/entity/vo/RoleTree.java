package xyz.ring2.admin.core.entity.vo;

import lombok.Data;
import xyz.ring2.admin.core.entity.Role;

import java.util.List;

/**
 * @author :     weiquanquan
 * @date :       2020/2/10 08:29
 * description:
 **/
@Data
public class RoleTree extends Role {

    private List<PermissionTree> children;

}
