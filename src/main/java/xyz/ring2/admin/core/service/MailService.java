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

    /**
     *
     * @param from 发送方
     * @param to    接收方
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String from, String to, String subject, String content);

    /**
     *
     * @param from 发送方
     * @param to    接收方
     * @param subject 主题
     * @param content 内容
     * @param file 发送的文件
     */
    void sendAttachFileMail(String from, String to, String subject, String content, File file);

    void sendMailWithImg(String from, String to, String subject, String content, String[] srePath, String[] resIds);

    /**
     *
     * @param errorMessage 异常信息
     * @throws Exception
     */
    void sendExceptionMail(String errorMessage) throws Exception;

    void sendExceptionMail(String from, String to, String subject, String errorMessage, String templateFile) throws Exception;

    void sendMailAsync(String message);
}
