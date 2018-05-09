package com.shawnsofo.gilbarco.time.api;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentTimeServiceConfig {
    @Bean
    public Clock getSystemClock() {
        return Clock.systemDefaultZone();
    }
}