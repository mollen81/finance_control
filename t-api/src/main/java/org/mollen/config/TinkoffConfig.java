package org.mollen.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
public class TinkoffConfig {

    // Token is in .env file in the root of t-api module
    private String token = Dotenv.load().get("T_API");

    @Bean
    public InvestApi investApi() {
        return InvestApi.create(token);
    }

    public String getToken() {
        return token;
    }
}
