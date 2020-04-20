package xyz.ring2.admin.common;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ring2.admin.core.service.MailService;

/**
 * @author :     ring2
 * @date :       2020/2/28 08:32
 * description:  发送报警邮件线程
 **/
@Component
@Slf4j
@Setter
public class ExceptionMailSend extends Thread {

    @Autowired
    MailService mailService;

    private volatile String errorMessage;

    @Override
    public void run() {
        try {
            log.info("正在发送邮件");
            mailService.sendExceptionMail(errorMessage);
        } catch (Exception e) {
            log.error("发送报警邮件发生了异常-> {}",e.getMessage());
            e.printStackTrace();
        }
    }

}
