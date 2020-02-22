package xyz.ring2.admin.exception;

import lombok.Getter;
import xyz.ring2.admin.common.CommonStatus;

/**
 * @author :     weiquanquan
 * @date :       2020/2/22 11:40
 * description:  服务 异常处理类
 **/
@Getter
public class ServiceException extends RuntimeException {
    private CommonStatus commonStatus;
    public ServiceException(){
        super("服务出现未知异常");
    }
    public ServiceException(CommonStatus exceptionStatus){
        super(exceptionStatus.getMessage());
        this.commonStatus = exceptionStatus;
    }
}
