package xyz.ring2.admin.core.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scripting.support.RefreshableScriptTargetSource;
import org.springframework.web.bind.annotation.*;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.Role;
import xyz.ring2.admin.core.entity.vo.PermissionTree;
import xyz.ring2.admin.core.entity.vo.RoleTree;
import xyz.ring2.admin.core.service.IRolePermissionRelService;
import xyz.ring2.admin.core.service.IRoleService;
import xyz.ring2.admin.core.service.impl.RolePermissionRelServiceImpl;
import xyz.ring2.admin.core.service.impl.RoleServiceImpl;
import xyz.ring2.admin.utils.FieldCheckUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :     weiquanquan
 * @date :       2020/2/8 14:10
 * description:  角色相关接口
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IRolePermissionRelService relService;

    /**
     * Tree => data：{
     * id:
     * name:
     * nameZh:
     * children:[
     * { permissionObj}
     * ]
     * }
     *
     * @return 获取角色及角色所拥护的权限，以树形结构返回，如上
     */
    @GetMapping("/listTree")
    public RestResult<List> getRoleListWithPower() {
        List<RoleTree> roleTreeList = roleService.selectRoleTreeWithPermission();
        if (roleTreeList.size() > 0) {
            return RestResult.success(roleTreeList);
        }
        return RestResult.failureOfQuery();
    }

    /**
     * @param perId
     * @param roleId
     * @return 根据角色id及权限id删除角色权限关联表中对应的数据
     * 即删除该角色下的权限
     */
    @DeleteMapping("/delRolePermission/{perId}/{roleId}")
    public RestResult<List> delRolePermission(@PathVariable Integer perId, @PathVariable Long roleId) {
        //TODO 删除了角色下的二级以及以上权限应当同时删除其子权限
        if (perId != null && roleId != null) {
            boolean b = relService.delRelByRoleAndPerId(perId, roleId);
            if (b) {
                List<PermissionTree> children = roleService.selectRoleTreeWithPermission().stream().
                        filter(roleTree -> roleTree.getId().equals(roleId)).
                        collect(Collectors.toList()).get(0).getChildren();

                return RestResult.success(children);
            }
        } else {
            return RestResult.failureOfParam();
        }
        return RestResult.failure();
    }

    /**
     * @param role
     * @return 添加角色
     */
    @PostMapping
    public RestResult saveRole(@RequestBody Role role) {
        boolean fieldsNotEmpty = FieldCheckUtils.isFieldsNotEmpty(role, Role::getName, Role::getNameZh);
        if (fieldsNotEmpty) {
            role.setName("ROLE_" + role.getName());
            int size = roleService.lambdaQuery().eq(Role::getName, role.getName()).list().size();
            if (size > 0){
                return RestResult.failureOfRepeatName();
            }
                boolean save = roleService.save(role);
            if (save) {
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failureOfParam();
    }

    /**
     * @param id
     * @return 根据角色id删除角色, 同时删除用户角色、角色权限关联表信息
     */
    @DeleteMapping("/{id}")
    public RestResult removeRole(@PathVariable Long id) {
        if (id != null) {
            boolean b = roleService.removeRole(id);
            if (b) {
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failureOfParam();
    }
    @PutMapping
    public RestResult updateRole(@RequestBody Role role){
        boolean fieldsNotEmpty = FieldCheckUtils.isFieldsNotEmpty(role, Role::getName, Role::getNameZh,Role::getId);
        if (fieldsNotEmpty){
            role.setName("ROLE_"+role.getName());
            boolean b = roleService.updateById(role);
            if (b){
                return RestResult.success();
            }
            return RestResult.failure();
        }
        return RestResult.failureOfParam();
    }
    /**
     * 保存某角色的权限信息
     *
     * @return
     */
    @PostMapping("/saveRights/{setRightsRoleId}")
    public RestResult distributePermission(@PathVariable("setRightsRoleId") Integer id,@RequestBody JSONObject permissionIds) {
        Object permissionIds1 = permissionIds.get("permissionIds");
        List<Integer> list = JSONUtil.toList(JSONUtil.parseArray(permissionIds1), Integer.class);
        boolean b = roleService.saveRolePermission(id, list);
        if (b){
            return RestResult.success();
        }
        return RestResult.failure();
    }

    /**
     *
     * @return 获取所有的角色列表
     */
    @GetMapping("list")
    public RestResult<List> getAllRoles(){
        List<Role> list = roleService.list();
        return RestResult.success(list);
    }
}

