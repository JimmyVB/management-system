package com.dev.managementsystem.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(50); // Máximo 50 requests por segundo
    }
}