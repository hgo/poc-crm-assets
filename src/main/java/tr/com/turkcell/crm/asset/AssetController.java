package tr.com.turkcell.crm.asset;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/api")
public class AssetController
{

    private final AssetService assetService;

    public AssetController(AssetService assetService)
    {
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public ResponseEntity<List<Asset>> findAsset(@RequestParam String customerId)
    {

        return ResponseEntity.ok(assetService.findByCustomerId(customerId));

    }
}
