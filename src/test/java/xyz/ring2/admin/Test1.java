package xyz.ring2.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ring2.admin.core.entity.User;
import xyz.ring2.admin.core.entity.vo.UserVo;
import xyz.ring2.admin.core.mapper.UserMapper;
import xyz.ring2.admin.core.service.IUserService;
import xyz.ring2.admin.utils.FieldCheckUtils;
import xyz.ring2.admin.utils.JwtTokenUtil;

import java.util.List;
import java.util.Optional;

/**
 * @author :  weiquanquan
 * @date :   2020/2/3 12:50
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testUser() {
        User user = new User();
        user.setUsername("sanmao");
        user.setPassword(passwordEncoder.encode("123456"));
//        boolean save = userService.save(user);
//        Assert.assertEquals(true,save);
        //System.out.println(JWTConfig.tokenHead + jwtTokenUtil.generateToken(user));
//        String token = "tutor-iteyJhbGciOiJIUzUxMiJ9.eyJzdWIiOm51bGwsImNyZWF0ZWQiOjE1ODA4MDg0NDczOTcsImV4cCI6MTU4MDg5NDg0N30.7MTxtQycgHr3poNmzGpVY1ieCJ9WFIn2d2UiOEVb89Mj0f90Hqh2LXoXrde4Hq29ur0PQ9D-3tp4rPl-4Zg5DA";
//        String newToken = token.substring(JWTConfig.tokenHead.length());
        Assert.assertEquals("sanmao", jwtTokenUtil.getUserNameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW5tYW8iLCJjcmVhdGVkIjoxNTgwODA4ODg0MTA3LCJleHAiOjE1ODA4OTUyODR9.zJMSOADDE0DCiTLAVFKNr1YZgLmZqbdcapv-E7lOklKyd7PQqXplcLI2hcSxmhpANas5CFkFn5bb52j3v7N9tw"));

    }

    @Test
    public void test2342() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", "sanmao");
        Page<UserVo> userPage = userMapper.selUserListWithRoleInfo(new Page<User>(1, 2), queryWrapper);
        List<UserVo> records = userPage.getRecords();
        records.forEach(record -> {
            System.out.println(record.getUsername());
        });
    }

    @Test
    public void testS() {
        /*User user11= new User();
        Optional<User> user = Optional.of(user11);
        Optional<String> s = user.map(User::getUsername);
*/
        User user = new User();
        user.setId(1L);
        user.setUsername("张三");
        user.setPassword("");
//        boolean username = FieldCheckUtils.isFieldsNotNull(user, "username");
//        Assert.assertTrue(username);
      //  System.out.println(FieldCheckUtils.isFieldsNotEmpty(user, "username", "password"));
    }
}
