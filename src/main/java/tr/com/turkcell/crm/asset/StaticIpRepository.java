package tr.com.turkcell.crm.asset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaticIpRepository extends MongoRepository<StaticIp, String>
{


}
