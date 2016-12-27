package com.eventrock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
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

    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

}
