package tr.com.turkcell.crm.asset.impl;

import org.springframework.stereotype.Service;
import tr.com.turkcell.crm.asset.Asset;
import tr.com.turkcell.crm.asset.AssetRepository;
import tr.com.turkcell.crm.asset.NetworkService;
import tr.com.turkcell.crm.asset.StaticIp;
import tr.com.turkcell.crm.asset.StaticIpRepository;

import java.util.Optional;
import java.util.Random;

@Service
class NetworkServiceImpl implements NetworkService
{

    private final StaticIpRepository staticIpRepository;
    private final AssetRepository assetRepository;

    NetworkServiceImpl(StaticIpRepository staticIpRepository, AssetRepository assetRepository)
    {
        this.staticIpRepository = staticIpRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public void setStaticIp(Asset asset, String offerId)
    {
        final Optional<Asset.Offer> relatedOffer = asset.getOffers()
                .stream()
                .filter(offer -> offer.getOfferId().equals(offerId))
                .findFirst();

        final Optional<String> newIp = relatedOffer.flatMap(this::setStaticIpProperty);

        newIp.ifPresent(ip -> {
            assetRepository.save(asset);

            final StaticIp staticIp = new StaticIp();
            staticIp.setCustomerId(asset.getCustomerId());
            staticIp.setValue(ip);
            staticIpRepository.save(staticIp);
        });

    }

    private Optional<String> setStaticIpProperty(Asset.Offer offer)
    {
        final Optional<Asset.AssetProperty> staticIpProperty = offer.getProperties()
                .stream()
                .filter(assetProperty -> assetProperty.getName().toLowerCase().contains("static"))
                .findFirst();

        return staticIpProperty.map(assetProperty -> setStaticIpPropertyValue(assetProperty));
    }

    private String setStaticIpPropertyValue(Asset.AssetProperty assetProperty)
    {
        final String newIp = getNewIp();
        assetProperty.setValue(newIp);
        return newIp;
    }

    private String getNewIp()
    {
        final Random r = new Random(System.currentTimeMillis());

        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }
}
