package xyz.ring2.admin.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 * @Author Sans
 * @CreateTime 2019/10/1 22:56
 */
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    /**
     * 密钥KEY
     */
    public static String secret;
    /**
     * TokenKey
     */
    public static String tokenHeader;
    /**
     * Token前缀字符
     */
    public static String tokenHead;
    /**
     * 过期时间
     */
    public static Integer expiration;
    /**
     * 不需要认证的接口
     */
    public static String antMatchers;


    public void setSecret(String secret) {
        JWTConfig.secret = secret;
    }

    public void setTokenHeader(String tokenHeader) {
        JWTConfig.tokenHeader = tokenHeader;
    }

    public void setTokenHead(String tokenHead) {
        JWTConfig.tokenHead = tokenHead;
    }

    public void setExpiration(Integer expiration) {
        JWTConfig.expiration = expiration * 1000;
    }

    public void setAntMatchers(String antMatchers) {
        JWTConfig.antMatchers = antMatchers;
    }
}