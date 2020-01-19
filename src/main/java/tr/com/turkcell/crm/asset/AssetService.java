package tr.com.turkcell.crm.asset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.turkcell.crm.order.OrderCreated;

import java.util.Optional;

@Service
public class AssetService
{
    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper)
    {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    public void orderCreated(OrderCreated orderCreated)
    {
        final Optional<Asset> savedAsset = assetRepository.findByOrderId(orderCreated.getId());
        if (!savedAsset.isPresent())
        {
            final Asset asset = assetMapper.map(orderCreated);
            assetRepository.save(asset);
        }
    }

    public Optional<Asset> findByCustomerId(String customerId)
    {
        return assetRepository.findByCustomerId(customerId);
    }
}
