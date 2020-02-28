package xyz.ring2.admin;

import freemarker.template.TemplateException;
import net.bytebuddy.asm.Advice;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ring2.admin.common.ExceptionMailSend;
import xyz.ring2.admin.core.service.MailService;
import xyz.ring2.admin.core.service.impl.MailServiceImpl;

import javax.mail.MessagingException;
import java.io.IOException;

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

    @Autowired
    MailService mailService;

    @Value("${admin.mail.from}")
    String mailFrom;
    @Value("${admin.mail.to}")
    String mailTo;

    @Autowired
    ExceptionMailSend exceptionMailSend;

    @Test
    public void generatePass(){
        String root = stringEncryptor.encrypt("root");
        String wqq123 = stringEncryptor.encrypt("wqq123");
        System.out.println(root);
        System.out.println(wqq123);
    }

    @Test
    public void testMail(){
        // 测试简单邮件通过
        mailService.sendSimpleMail(mailFrom,mailTo,"测试主题","测试内容");
        //测试模板邮件通过
        try {
            mailService.sendExceptionMail("统发生了异常，这个是异常信息ksdjfsjldfks");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件发生错误");
        }
    }

    @Test
    public void testThreadMail(){

        try {
            Thread.sleep(22000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
