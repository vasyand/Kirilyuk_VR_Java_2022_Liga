package ru.homework.tasktracker.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static ru.homework.tasktracker.model.entity.Role.ADMIN;
import static ru.homework.tasktracker.model.entity.Role.USER;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http

                .authorizeRequests()
                .antMatchers("/api/v2/registration")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/users/**")
                .hasRole(USER.name())
                .antMatchers("/api/v2/tasks/**")
                .hasRole(USER.name())
                .antMatchers("/api/v2/projects/**")
                .hasRole(USER.name())
                .antMatchers("/api/v2/comments/**")
                .hasRole(USER.name())
                .antMatchers("/api/**")
                .hasRole(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().loginProcessingUrl("/api/v2/login")
                .and()
                .logout()
                .logoutUrl("/api/v2/logout");

    }


}
