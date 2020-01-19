package tr.com.turkcell.crm.asset.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import tr.com.turkcell.crm.asset.AssetService;
import tr.com.turkcell.crm.asset.EventConsumer;
import tr.com.turkcell.crm.asset.Topics;
import tr.com.turkcell.crm.order.OrderCreated;

public class EventConsumerImpl implements EventConsumer
{

    private final AssetService assetService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    public EventConsumerImpl(AssetService assetService, ObjectMapper objectMapper)
    {
        this.assetService = assetService;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(topics = Topics.ASSETS, groupId = "group_id")
    public void consume(String message)
    {
        try
        {
            final OrderCreated orderCreated = objectMapper.readValue(message, OrderCreated.class);

            assetService.orderCreated(orderCreated);
        }
        catch (JsonProcessingException e)
        {
            //POC kapsaminda Schema Registry kullanilmamistir
            logger.error("unable to process json", e);
        }
        logger.info(String.format("$$ -> Consumed Message -> %s", message));

    }


}
