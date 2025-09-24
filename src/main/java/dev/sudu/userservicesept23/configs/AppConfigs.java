package dev.sudu.userservicesept23.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class AppConfigs {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();

        return httpSecurity.authorizeHttpRequests(
                authorize -> authorize.anyRequest().permitAll()
        ).build();
    }

    @Bean
    public SecretKey getSecretKey() {
        // algo
        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;

        // signature
        SecretKey secretKey = macAlgorithm.key().build();

        return secretKey;
    }
}
