package tr.com.turkcell.crm.asset;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "staticIps")
public class StaticIp
{
    @Id
    private String id;
    private String customerId;
    private String value;


}
