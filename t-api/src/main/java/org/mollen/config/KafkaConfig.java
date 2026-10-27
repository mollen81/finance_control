package org.mollen.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String MARKET_DATA_HISTORICAL_TOPIC = "market.data.historical";

    @Bean
    public NewTopic historicalDataTopic() {
        return TopicBuilder.name(MARKET_DATA_HISTORICAL_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
