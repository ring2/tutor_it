package xyz.ring2.admin.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.service.impl.UserServiceImpl;
import xyz.ring2.admin.utils.FieldCheckUtils;

import javax.validation.constraints.AssertFalse;
import java.util.Map;
import java.util.Optional;

/**
 * @author :  weiquanquan
 * @date :   2020/2/5 14:05
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/list")
    public RestResult<Map> getUserList(Integer pageNo, Integer pageSize, String username){
        Map<String, Object> map = userService.selUserListWithRoleInfo(pageNo, pageSize, username);
        if (map.size() > 0){
            return RestResult.success(map);
        }
        return RestResult.failureOfSelect();
    }

    @PutMapping("/{id}/{status}")
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

    @DeleteMapping("/{id}")
    public RestResult deleteUser(@PathVariable Long id){
        if (id == null){
            return RestResult.failureOfParam();
        }
        if (userService.removeById(id)){
            return RestResult.success();
        }
        return RestResult.failure();
    }
}
