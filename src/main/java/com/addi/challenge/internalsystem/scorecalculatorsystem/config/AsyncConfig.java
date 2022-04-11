package com.addi.challenge.internalsystem.scorecalculatorsystem.config;

import com.addi.challenge.internalsystem.scorecalculatorsystem.service.AsyncRemoteCallService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async Process-");
        executor.initialize();
        return executor;
    }

    @Bean
    public AsyncRemoteCallService asyncRemoteCallService() {
        return new AsyncRemoteCallService();
    }
}
