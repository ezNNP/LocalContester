package ru.stray27.simplecontester.backend.runner.configuration;

import com.github.codeboy.piston4j.api.Piston;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PistonConfiguration {
    @Bean
    public Piston piston() {
        return Piston.getDefaultApi();
    }
}
