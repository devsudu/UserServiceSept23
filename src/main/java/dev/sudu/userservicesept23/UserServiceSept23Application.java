package dev.sudu.userservicesept23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceSept23Application {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceSept23Application.class, args);
    }

}
