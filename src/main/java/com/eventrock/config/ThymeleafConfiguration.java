package com.eventrock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@Configuration
public class ThymeleafConfiguration {
    @Bean
    public ServletContextTemplateResolver templateResolver(){
        ServletContextTemplateResolver webTemplateResolver = new ServletContextTemplateResolver();
        webTemplateResolver.setPrefix("/WEB-INF/templates/");
        webTemplateResolver.setSuffix(".html");
        webTemplateResolver.setTemplateMode("HTML5");
        webTemplateResolver.setCharacterEncoding("UTF-8");
        webTemplateResolver.setOrder(2);
        webTemplateResolver.setCacheable(false);
        return webTemplateResolver;
    }
}
