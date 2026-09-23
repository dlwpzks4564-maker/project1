package com.logic.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//레포지스토리 나 컨트롤러 가 아닌 주입 + Config 으로 따로 선언해서 주입해야한다.
@Configuration
public class PasswordConfig { //컨트롤러 : vue에 데이터 값을 받아서 그대로 인코딩 해준다.
    //생성자 주입 식 BCryptPasswordEncoder : 암호화 인코더 비대칭이라서 복호화X
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
