package com.fluxapp.todoflux;

import com.fluxapp.todoflux.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TodoFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoFluxApplication.class, args);
    }

}
