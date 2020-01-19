package tr.com.turkcell.crm.asset;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "assets")
@Data
public class Asset
{
    @Id
    private String id;

    private String customerId;
    private Date createdAt = new Date();
    private AssetStatus status = AssetStatus.NONE;

    private String orderId;

    private List<Offer> offers;

    @Data
    public static class Offer
    {
        private String offerId;
        private String offerName;
        private List<AssetProperty> properties;
    }

    @Data
    public static class AssetProperty
    {
        private String name;
        private String value;
    }
}
