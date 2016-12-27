package com.eventrock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;

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
                .antMatchers("/", "/home","/signUp", "/event/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/organizer/**").hasRole("ORGANIZER")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/hello").hasRole("USER")
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("organizer").password("password").roles("USER","ORGANIZER")
                .and()
                .withUser("admin").password("password").roles("ADMIN","USER","ORGANIZER");
    }
}