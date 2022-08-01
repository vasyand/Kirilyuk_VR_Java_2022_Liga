package ru.homework.tasktracker.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.homework.tasktracker.model.entity.Role.ADMIN;
import static ru.homework.tasktracker.model.entity.Role.USER;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/api/v1/registration").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/users/**",
                        "/api/v1/projects/**",
                        "/api/v1/comments/**")
                .hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/api/v1/tasks/**")
                .hasAnyRole(USER.name(), ADMIN.name())
                .anyRequest()
                .hasRole(ADMIN.name())
                .and()
                .formLogin().loginProcessingUrl("/api/v1/auth/login")
                .successForwardUrl("/api/v1/auth/login/success")
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .logoutSuccessUrl("/api/v1/auth/logout/success").permitAll();

    }

}
