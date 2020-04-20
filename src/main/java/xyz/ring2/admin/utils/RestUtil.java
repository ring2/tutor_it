package xyz.ring2.admin.utils;

import cn.hutool.json.JSONUtil;
import xyz.ring2.admin.common.RestResult;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author :  weiquanquan
 * @date :   2020/2/4 13:45
 **/
public class RestUtil<T> {

    private RestUtil(){

    }
    public static void responseJsonMsg(ServletResponse response,RestResult restResult){
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            out = response.getWriter();
            out.println(JSONUtil.parse(restResult));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null){
                out.flush();
                out.close();
            }

        }

    }
}
