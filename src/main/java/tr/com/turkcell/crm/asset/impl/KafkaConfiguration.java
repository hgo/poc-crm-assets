package tr.com.turkcell.crm.asset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import tr.com.turkcell.crm.asset.AssetService;
import tr.com.turkcell.crm.asset.EventConsumer;
import tr.com.turkcell.crm.asset.EventProducer;

@Configuration
@Profile("!test")
public class KafkaConfiguration
{
    @Bean
    EventProducer eventProducer(KafkaTemplate<String, String> kafkaTemplate)
    {
        return new EventProducerImpl(kafkaTemplate);
    }

    @Bean
    @Lazy(false)
    EventConsumer eventConsumer(AssetService assetService, ObjectMapper objectMapper)
    {
        return new EventConsumerImpl(assetService, objectMapper);
    }

}
