package xyz.ring2.admin.core.controller;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.entity.vo.CaptchaVo;
import xyz.ring2.admin.core.service.IUserService;
import xyz.ring2.admin.core.service.impl.UserServiceImpl;
import xyz.ring2.admin.security.jwt.JWTConfig;
import xyz.ring2.admin.utils.JwtTokenUtil;
import xyz.ring2.admin.utils.RedisUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 16:02
 **/
@RestController
@Slf4j
public class LoginController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IUserService userService;

    /**
     * @return 根据用户的账号密码及验证码进行校验登陆并校验验证码
     */
    @PostMapping("/login")
    public RestResult<Map> login(@RequestBody CaptchaVo captchaVo) {
        Map<String, String> data = new HashMap<>();
        String username = captchaVo.getUsername();
        String password = captchaVo.getPassword();
        String key = captchaVo.getKey();
        String result = captchaVo.getResult();
        // 后端同样需要验证,用户名密码不能为空
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password) || StrUtil.isEmpty(key)) {
            return RestResult.failureOfParam();
        }
        // 首先判断验证码是否有效
        if (StrUtil.isNotEmpty(result) && redisUtil.get(key).equals(result.trim())) {
            Boolean valid = userService.validateUser(username, password);
            if (valid) {
                data.put("token", jwtTokenUtil.generateToken(new User().setUsername(username)));
                data.put("tokenHead", JWTConfig.tokenHead);
                return RestResult.success(data);
            } else {
              return RestResult.failure();
            }
        }
        return RestResult.failureOfCaptcha();
    }

    // 获取验证码,随机生成标识该验证码的key 设置进redis 5分钟失效
    @GetMapping("/captcha")
    public RestResult<String> getCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 40);
        captcha.setLen(2);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        String answer = captcha.text();// 获取运算的结果：5
        String s = captcha.toBase64();
        String key = UUID.randomUUID().toString();
        redisUtil.setEx(key, answer, 5, TimeUnit.MINUTES);
        return RestResult.success(new CaptchaVo().setKey(key).setCaptcha(s));
    }
}
