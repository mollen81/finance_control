package org.mollen;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.mollen.config.TinkoffConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(InvestApiApplication.class, args);
    }
}