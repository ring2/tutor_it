package xyz.ring2.admin.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author :     ring2
 * @date :       2020/4/16 09:37
 * description:  全局日期格式化配置
 **/
@Configuration
public class LocalDateTimeSerializerConfig {

    /**
     * 某些bean字段可能需要yyyy-MM-dd的格式化，则在该字段上添加注解:
     *
     * @JsonFormat(locale = "zh",timezone="GMT+8",pattern="yyyy-MM-dd")
     */

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
    }
}
