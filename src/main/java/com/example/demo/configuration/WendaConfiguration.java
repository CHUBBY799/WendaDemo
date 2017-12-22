package com.example.demo.configuration;

import com.example.demo.interceptor.passportIntercepetor;
import com.example.demo.interceptor.requiredIntercepetor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WendaConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private passportIntercepetor passportIntercepetor;
    @Autowired
    private requiredIntercepetor requiredIntercepetor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportIntercepetor);
        registry.addInterceptor(requiredIntercepetor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
