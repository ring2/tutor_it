package xyz.ring2.admin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author :  ring2
 * @date :   2020/2/3 12:58
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommonStatus {
    /**
     * 操作成功
     */
    SUCCESS("操作成功", 2000),
    /**
     *操作失败
     */
    FAILURE("操作失败",4000),
    /**
     *用户名重复
     */
    REPEAT_NAME("用户名重复",4001),
    /**
     *权限不足
     */
    FAILED_AUTHORITY("权限不足",3000),
    /**
     *暂未登录
     */
    FAILED_NOT_LOGIN("暂未登录",3001),
    /**
     *参数无效
     */
    FAILED_BAD_PARAM("参数无效",4003),
    /**
     *密码错误
     */
    FAILED_PASSWORD("密码错误",3002),
    /**
     *未查询到此用户
     */
    FAILED_FOUND_NAME("未查询到此用户",3004),
    /**
     *验证码错误
     */
    FAILED_CAPTCHA("验证码错误",4005),
    /**
     * 请勿重复提交数据
     */
    FAILED_REPEAT_POST("请勿重复提交数据",4006);

    private String message;
    private Integer statusCode;

}
