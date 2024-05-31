package t1.openschool.jwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JwtdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtdemoApplication.class, args);
    }
}
