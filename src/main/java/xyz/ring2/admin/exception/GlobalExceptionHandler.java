package xyz.ring2.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.ring2.admin.common.CommonStatus;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.core.service.MailService;

import java.util.List;

import static jdk.nashorn.internal.objects.NativeArray.lastIndexOf;

/**
 * @author :     ring2
 * @date :       2020/2/22 11:38
 * description:  全局异常处理器
 * 鄙见： Controller 层主要作为 “转发”层使用，对参数进行校验并
 * 转发给service层，service负责主要的业务逻辑，故绝大部分的错误和异常发生在
 * service层中，故service层编写相关异常类并需要时手动抛出异常，controller层调用
 * service层代码时如收到异常则此处理器（globalExceptionHandler）进行捕获其异常信息及
 * 状态码进行返回给用户层
 **/
@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    MailService mailService;

    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception exception) {
        // 如果发生的异常为Service异常
        if (exception instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) exception;
            Integer statusCode = serviceException.getCommonStatus().getStatusCode();
            String message = serviceException.getCommonStatus().getMessage();
            log.error("service层发生了异常【{}】,错误状态码【{}】", message, statusCode);
            return RestResult.failure(serviceException.getCommonStatus());
            // 发生的异常为用户参数校验失败
        } else if (exception instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            MethodArgumentNotValidException exception1 = (MethodArgumentNotValidException) exception;
            List<FieldError> fieldErrors = exception1.getBindingResult().getFieldErrors();
            if (fieldErrors.size() > 0) {
                fieldErrors.forEach(item -> {
                    sb.append(item.getField() + ": " + item.getDefaultMessage() + " &");
                });
            }
            String message = sb.toString();
            Integer code = CommonStatus.FAILED_BAD_PARAM.getStatusCode();
            log.warn("参数校验发生错误【{}】,错误状态码【{}】", message, code);
            return RestResult.failure(message, code);
        } else if (exception instanceof NullPointerException) {
            log.error("发生空指针异常【{}】", exception.getMessage());
        }
        // 其他未知异常
        String message = exception.getMessage();
        // 开启线程发送邮件告知管理员
        //mailService.sendMailAsync(message);
        log.error("发生了异常【{}】", message);
        return RestResult.failure("系统正在闹脾气(╯▔皿▔)╯，请稍后再试", CommonStatus.FAILURE.getStatusCode());
    }
}
