package com.Kalium.config;

import com.Kalium.util.OrderLoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public OrderLoggerInterceptor orderLoggerInterceptor() {
        return new OrderLoggerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderLoggerInterceptor())
                .addPathPatterns("/shoppingCart/checkout");
    }
}
