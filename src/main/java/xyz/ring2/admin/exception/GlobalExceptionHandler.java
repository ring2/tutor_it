package xyz.ring2.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.ring2.admin.common.RestResult;

/**
 * @author :     weiquanquan
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

    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception exception) {
        // 如果发生的异常为Service异常
        if (exception instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) exception;
            Integer statusCode = serviceException.getCommonStatus().getStatusCode();
            String message = serviceException.getCommonStatus().getMessage();
            log.error("service层发生了异常【{}】,错误状态码【{}】", message, statusCode);
            return RestResult.failure(serviceException.getCommonStatus());
        }
        // 其他未知异常
        String message = exception.getMessage();
        log.error("发生了异常【{}】", message);
        return RestResult.failure(message);
    }
}
