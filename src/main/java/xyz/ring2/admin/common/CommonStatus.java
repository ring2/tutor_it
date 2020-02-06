package xyz.ring2.admin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author :  weiquanquan
 * @date :   2020/2/3 12:58
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommonStatus {
    SUCCESS("操作成功", 200),
    FAILURE("操作失败",400),
    REPEAT_NAME("用户名重复",401),
    FAILED_AUTHORITY("权限不足",300),
    FAILED_NOT_LOGIN("暂未登录",402),
    FAILED_BAD_PARAM("参数无效",403);

    private String message;
    private Integer statusCode;

}
