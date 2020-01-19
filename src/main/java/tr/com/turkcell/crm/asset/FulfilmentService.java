package tr.com.turkcell.crm.asset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tr.com.turkcell.crm.order.OrderStatus;
import tr.com.turkcell.crm.order.OrderStatusChanged;

import java.util.function.Predicate;

@Service
public class FulfilmentService
{

    private static final Logger logger = LoggerFactory.getLogger(FulfilmentService.class);

    private final EventProducer eventProducer;
    private final ObjectMapper objectMapper;

    private final AssetRepository assetRepository;

    private static final Predicate<Asset.Offer> IS_FIBER_OFFER = offer -> offer.getOfferName().toLowerCase().contains("fiber");
    private final NetworkService networkService;

    public FulfilmentService(EventProducer eventProducer, ObjectMapper objectMapper, AssetRepository assetRepository, NetworkService networkService)
    {
        this.eventProducer = eventProducer;
        this.objectMapper = objectMapper;
        this.assetRepository = assetRepository;
        this.networkService = networkService;
    }

    @Scheduled(fixedRate = 60_000)
    public void fulfill()
    {
        logger.info("fulfilment started");

        assetRepository.findTop1ByStatusOrderByCreatedAtDesc(AssetStatus.NONE)
                .ifPresent(this::processAsset);

        logger.info("fulfilment end");
    }

    private void processAsset(Asset asset)
    {
        logger.info("processing asset {} of order {}" , asset.getId() , asset.getOrderId());
        setAssetStatus(asset, AssetStatus.IN_PROGRESS);

        notifyOrder(asset, OrderStatus.PROCESSING);

        asset
                .getOffers()
                .stream()
                .filter(IS_FIBER_OFFER)
                .findAny()
                .ifPresent(offer -> networkService.setStaticIp(asset, offer.getOfferId()));

        setAssetStatus(asset, AssetStatus.PROCESSED);

        notifyOrder(asset, OrderStatus.COMPLETED);
    }

    private void notifyOrder(Asset asset, OrderStatus status)
    {
        logger.info("notifying order {} of asset {} with status {}", asset.getOrderId(), asset.getId(), status);
        final OrderStatusChanged orderStatusChanged = new OrderStatusChanged();
        orderStatusChanged.setId(asset.getOrderId());
        orderStatusChanged.setStatus(status);
        try
        {
            eventProducer.sendMessage(objectMapper.writeValueAsString(orderStatusChanged));
        }
        catch (JsonProcessingException e)
        {
            //POC kapsaminda Schema Registry kullanilmamistir
            logger.error("unable to process json", e);
        }
    }

    private void setAssetStatus(Asset asset, AssetStatus status)
    {
        asset.setStatus(status);
        assetRepository.save(asset);
        logger.info("asset {} of order {} status setting to {}", asset.getId(), asset.getOrderId(), status);
    }
}
