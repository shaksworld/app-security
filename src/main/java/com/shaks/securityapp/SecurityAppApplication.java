package com.shaks.securityapp;

import com.shaks.securityapp.entity.Role;
import com.shaks.securityapp.entity.Users;
import com.shaks.securityapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class SecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAppApplication.class, args);
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService){
//        return args ->{
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_MANAGER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new Users(null, "John Travolta", "john", "john@gmail.com", "1234", new HashSet<>()));
//            userService.saveUser(new Users(null, "Faith Stephen", "faith", "faith@gmail.com", "0987", new HashSet<>()));
//
//            userService.addToUser("john", "ROLE_USER");
//            userService.addToUser("john", "ROLE_MANAGER");
//            userService.addToUser("faith", "ROLE_ADMIN");
//            userService.addToUser("faith", "ROLE_SUPER_ADMIN");
//        };
//    }

}
