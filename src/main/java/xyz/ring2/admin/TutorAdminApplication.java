package xyz.ring2.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import xyz.ring2.admin.common.TutorITApplication;

/**
 * TutorIT-Admin 启动类入口
 */
@TutorITApplication
@Slf4j
public class TutorAdminApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TutorAdminApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("服务启动成功，启动参数【{}】！",args.toString());
    }
}
