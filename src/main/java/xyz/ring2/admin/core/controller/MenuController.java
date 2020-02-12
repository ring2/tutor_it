package xyz.ring2.admin.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.vo.PermissionTree;
import xyz.ring2.admin.core.service.IPermissionService;
import xyz.ring2.admin.core.service.impl.PermissionServiceImpl;

import java.util.List;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 13:15
 **/
@RestController
@Slf4j
public class MenuController {

    @Autowired
    IPermissionService permissionService;

    /**
     * 根据用户的角色信息获取对应的权限树
     * @return
     */
    @GetMapping("/menu")
    public RestResult<List> getMenus() {
        List<PermissionTree> data ;
        data = permissionService.getMenuTree(0);
        return RestResult.success(data);
    }

}
