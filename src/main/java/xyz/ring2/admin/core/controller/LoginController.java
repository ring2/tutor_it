package xyz.ring2.admin.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.security.jwt.JWTConfig;
import xyz.ring2.admin.utils.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 16:02
 **/
@RestController
@Slf4j
public class LoginController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public RestResult<Map> login(@RequestBody User user){
        Map<String,String> data = new HashMap<>();
        data.put("token",jwtTokenUtil.generateToken(user));
        data.put("tokenHead", JWTConfig.tokenHead);
        return RestResult.success(data);
    }
}
