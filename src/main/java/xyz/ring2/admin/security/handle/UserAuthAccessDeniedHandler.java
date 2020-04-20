package xyz.ring2.admin.security.handle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import xyz.ring2.admin.common.RestResult;
import xyz.ring2.admin.utils.RestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户暂无权限处理类
 * @author :  weiquanquan
 * @date :   2020/2/4 14:09
 **/
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        RestUtil.responseJsonMsg(httpServletResponse, RestResult.failureOfAuthority());
    }
}
