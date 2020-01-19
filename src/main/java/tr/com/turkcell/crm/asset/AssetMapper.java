package tr.com.turkcell.crm.asset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.turkcell.crm.order.OrderCreated;

@Mapper(componentModel = "spring")
public interface AssetMapper
{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "offers",source = "orderLines")
    Asset map(OrderCreated orderCreated);
}
