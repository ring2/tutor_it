package xyz.ring2.admin.core.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author :     weiquanquan
 * @date :       2020/2/28 09:01
 * description:  定义邮件服务接口
 **/
@Transactional(rollbackFor = Exception.class)
public interface MailService {

    void sendSimpleMail(String from, String to, String subject, String content);

    void sendAttachFileMail(String from, String to, String subject, String content, File file);

    void sendMailWithImg(String from, String to, String subject, String content, String[] srePAth, String[] resIds);

    void sendExceptionMail(String errorMessage) throws Exception;

    void sendExceptionMail(String from, String to, String subject, String errorMessage, String templateFile) throws Exception;

    void sendMailAsync(String message);
}
