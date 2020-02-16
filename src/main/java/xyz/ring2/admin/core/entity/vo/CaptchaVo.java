package xyz.ring2.admin.core.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :     weiquanquan
 * @date :       2020/2/16 22:45
 * description:
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CaptchaVo {
    private String key;
    private String result;
    private String username;
    private String password;
    private String captcha;
}
