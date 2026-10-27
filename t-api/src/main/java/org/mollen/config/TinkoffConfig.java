package org.mollen.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Getter
@Configuration
public class TinkoffConfig {

    @Value("${t-invest.token}")
    private String token;

    @Bean
    public InvestApi investApi() {
        return InvestApi.create(token);
    }

}