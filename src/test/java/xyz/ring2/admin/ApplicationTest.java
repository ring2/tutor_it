package xyz.ring2.admin;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author :     weiquanquan
 * @date :       2020/2/14 14:35
 * description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TutorAdminApplication.class})
public class ApplicationTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void generatePass(){
        String root = stringEncryptor.encrypt("root");
        String wqq123 = stringEncryptor.encrypt("wqq123");
        System.out.println(root);
        System.out.println(wqq123);
    }
}
