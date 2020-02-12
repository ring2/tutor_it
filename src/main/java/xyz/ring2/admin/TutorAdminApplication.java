package xyz.ring2.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("xyz.ring2.admin.core.mapper")
@EnableTransactionManagement
public class TutorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorAdminApplication.class, args);
    }

}
