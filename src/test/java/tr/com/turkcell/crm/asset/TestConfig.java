package tr.com.turkcell.crm.asset;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig
{

    @Bean
    @Primary
    public StaticIpRepository StaticIpRepository()
    {
        return Mockito.mock(StaticIpRepository.class);
    }

    @Bean
    @Primary
    public AssetRepository AssetRepository()
    {
        return Mockito.mock(AssetRepository.class);
    }


    @Bean
    @Primary
    public EventConsumer eventConsumer()
    {
        return Mockito.mock(EventConsumer.class);
    }

    @Bean
    @Primary
    public EventProducer eventProducer()
    {
        return Mockito.mock(EventProducer.class);
    }

}
