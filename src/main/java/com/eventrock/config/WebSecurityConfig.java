package com.eventrock.config;

import com.eventrock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;

    @Autowired
    @Qualifier("userDetailsService")
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (environment.acceptsProfiles("h2")) {
            http
                .csrf()
                .disable()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/h2-console/**")
                    .permitAll();
        }

        http
            .authorizeRequests()
                .antMatchers("/", "/home","/registration", "/event/**").permitAll()
                .antMatchers("/hello").authenticated()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
            .logoutSuccessUrl("/login")
            .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((UserDetailsService) userService).passwordEncoder(
                passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}