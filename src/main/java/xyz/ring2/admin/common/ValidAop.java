package xyz.ring2.admin.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author :     ring2
 * @date :       2020/4/12 10:59
 * description:  暂时无用
 **/
@Aspect
@Component
public class ValidAop {

    @Pointcut("@annotation(javax.validation.Valid)")
    private void pointCut(){
    }

    /**
     *
     *
     */
    @Around("pointCut()")
    public RestResult around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0){
            for (Object arg : args) {
               if (arg.getClass() == BindingResult.class) {
                   BindingResult result = (BindingResult)arg;
                   List<FieldError> fieldErrors = result.getFieldErrors();
                   if (fieldErrors.size() > 0){
                      fieldErrors.forEach(item -> {
                          sb.append(item.getField()+": "+item.getDefaultMessage()+" &");
                      });
                   }
               }
            }
        }
        Object proceed = proceedingJoinPoint.proceed();
        return RestResult.success();
    }


}
