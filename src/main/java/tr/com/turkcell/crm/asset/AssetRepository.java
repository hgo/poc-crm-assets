package tr.com.turkcell.crm.asset;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AssetRepository extends MongoRepository<Asset, String>
{
    Optional<Asset> findTop1ByStatusOrderByCreatedAtDesc(AssetStatus status);

    Optional<Asset> findByCustomerId(String customerId);

    Optional<Asset> findByOrderId(String id);
}
