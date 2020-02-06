package xyz.ring2.admin.core.entity.vo;

import lombok.Data;
import xyz.ring2.admin.core.entity.User;

import java.io.Serializable;

/**
 * @author :     weiquanquan
 * @date :       2020/2/5 14:52
 * description:
 **/
@Data
public class UserVo extends User implements Serializable {
    private String roleName;
}
