package org.mollen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
public class TinkoffConfig {

    @Value("${t-invest.token}")
    private String sandboxToken;

    @Bean
    public InvestApi investApi() {
        // Sandbox
        return InvestApi.create(sandboxToken);
    }
}
