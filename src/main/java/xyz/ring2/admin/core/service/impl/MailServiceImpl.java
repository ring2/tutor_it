package xyz.ring2.admin.core.service.impl;

import cn.hutool.core.util.StrUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import xyz.ring2.admin.core.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :     weiquanquan
 * @date :       2020/2/28 09:03
 * description:  邮件服务
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${admin.mail.from}")
    private String defaultMailFrom;

    @Value("${admin.mail.to}")
    private String defaultMailTo;

    @Value("${mail.exception.ftl}")
    private String defaultMailFtl;


    /**
     * @param from
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String from, String to, String subject, String content) {
        if (StrUtil.isEmpty(from)) {
            from = defaultMailFrom;
        }
        if (StrUtil.isEmpty(to)) {
            to = defaultMailTo;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAttachFileMail(String from, String to, String subject, String content, File file) {
        if (StrUtil.isEmpty(from)) {
            from = defaultMailFrom;
        }
        if (StrUtil.isEmpty(to)) {
            to = defaultMailTo;
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);
            mimeMessageHelper.addAttachment(file.getName(), file);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendMailWithImg(String from, String to, String subject, String content, String[] srePath, String[] resIds) {
        if (StrUtil.isEmpty(from)) {
            from = defaultMailFrom;
        }
        if (StrUtil.isEmpty(to)) {
            to = defaultMailTo;
        }
        if (srePath.length != resIds.length) {
            System.out.println("发送失败");
            return;
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            for (int i = 0; i < srePath.length; i++) {
                FileSystemResource res = new FileSystemResource(new File(srePath[i]));
                mimeMessageHelper.addInline(resIds[i], res);
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendExceptionMail(String errorMessage) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(defaultMailFrom);
        helper.setTo(defaultMailTo);
        helper.setSubject("tutor-admin 系统出现未知错误");
        Map<String, String> exceptionMessage = new HashMap<>(16);
        exceptionMessage.put("exceptionMessage", errorMessage);
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(defaultMailFtl);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, exceptionMessage);
        helper.setText(html, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendExceptionMail(String from, String to, String subject, String errorMessage, String templateFile) throws Exception {
        if (StrUtil.isEmpty(from)) {
            from = defaultMailFrom;
        }
        if (StrUtil.isEmpty(to)) {
            to = defaultMailTo;
        }
        if (StrUtil.isEmpty(templateFile)) {
            templateFile = defaultMailFtl;
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        Map<String, String> exceptionMessage = new HashMap<>(16);
        exceptionMessage.put("exceptionMessage", errorMessage);
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateFile);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, exceptionMessage);
        helper.setText(html, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendMailAsync(String message) {
        new Thread(() -> {
            try {
                sendExceptionMail(message);
                log.info("邮件发送成功");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("邮件发送失败，失败原因-> {}", e.getMessage());
            }
        }).start();
    }
}
