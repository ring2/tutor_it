package xyz.ring2.admin.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.Permission;
import xyz.ring2.admin.core.entity.vo.PermissionTree;
import xyz.ring2.admin.core.service.IPermissionService;
import xyz.ring2.admin.core.service.impl.PermissionServiceImpl;

import java.util.List;

/**
 * @author :     weiquanquan
 * @date :       2020/2/7 15:15
 * description:  用户权限相关操作
 **/
@RestController
@RequestMapping("/rights")
public class RightsController {

    @Autowired
    IPermissionService permissionService;

    /**
     *
     * @return 获取所有的权限信息
     */
    @GetMapping("/list")
    public RestResult<List> getRightsList(){
        List<Permission> list = permissionService.list();
        if (list.size() > 0){
            return RestResult.success(list);
        }
        return RestResult.failure();
    }

    /**
     *
     * @return 获取当前登录用户所有的权限信息以树形结构返回
     */
    @GetMapping("/listTree")
    public RestResult<List> getRightsListTree(){
        List<PermissionTree> data ;
        data = permissionService.getMenuTree(0);
        return RestResult.success(data);
    }
}
