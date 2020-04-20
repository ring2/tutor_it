package xyz.ring2.admin.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.entity.UserRoleRel;
import xyz.ring2.admin.core.service.IUserRoleRelService;
import xyz.ring2.admin.core.service.IUserService;
import xyz.ring2.admin.utils.FieldCheckUtils;

import java.util.Map;

/**
 * @author :  weiquanquan
 * @date :   2020/2/5 14:05
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IUserRoleRelService userRoleRelService;

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param username
     * @return 获取用户列表并进行分页
     */
    @GetMapping("/list")
    public RestResult<Map> getUserList(Integer pageNo, Integer pageSize, String username){
        Map<String, Object> map = userService.selUserListWithRoleInfo(pageNo, pageSize, username);
        if (map.size() > 0){
            return RestResult.success(map);
        }
        return RestResult.failureOfQuery();
    }

    /**
     *
     * @param id
     * @param status
     * @return 根据用户id对用户的状态进行改变 ==》 启用 or 禁用
     */
    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('admin')")
    public RestResult updateUserStatus(@PathVariable Long id,@PathVariable Boolean status){
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        boolean b = userService.updateById(user);
        if (b){
            return RestResult.success();
        }
        return RestResult.failure();
    }

    /**
     *
     * @param user 用户实体
     * @return 添加用户
     */
    @PostMapping
    public RestResult saveUser(@RequestBody User user){
        boolean flag = FieldCheckUtils.isFieldsNotEmpty
                (user, User::getUsername, User::getPassword);
        if (!flag){
            return RestResult.failureOfParam();
        }
        if (userService.saveUser(user)){
            return RestResult.success();
        }
        return RestResult.failure();
    }

    /**
     *  修改用户信息
     * @param user
     * @return
     */
    @PutMapping
    public RestResult updateUser(@RequestBody User user){
        boolean flag = FieldCheckUtils.isFieldsNotEmpty
                (user,User::getId, User::getTelephone, User::getEmail);
        if (!flag){
            return RestResult.failureOfParam();
        }
        if (userService.updateById(user)){
            return RestResult.success();
        }
        return RestResult.failure();
    }

    /**
     *  删除用户，同时删除用户角色关联表中的关联关系
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public RestResult deleteUser(@PathVariable Long id){
        if (id == null){
            return RestResult.failureOfParam();
        }
        boolean delFlag = userRoleRelService.remove(new QueryWrapper<UserRoleRel>().eq("user_id", id));
        if (userService.removeById(id) && delFlag){
            return RestResult.success();
        }
        return RestResult.failure();
    }

    @PutMapping("/updateUserRole/{userId}/{selectedRoleId}")
    public RestResult updateUserRole(@PathVariable Integer userId,@PathVariable("selectedRoleId") Integer roleId){
//        LambdaUpdateChainWrapper<UserRoleRel> eq = userRoleRelService.lambdaUpdate().eq(UserRoleRel::getUserId, userId);
        UpdateWrapper<UserRoleRel> userId1 = new UpdateWrapper<UserRoleRel>().eq("user_id", userId);
        boolean b = userRoleRelService.saveOrUpdate(new UserRoleRel().setUserId(userId).setRoleId(roleId), userId1);
        if (b){
            return RestResult.success();
        }
        return RestResult.failure();

    }
}
