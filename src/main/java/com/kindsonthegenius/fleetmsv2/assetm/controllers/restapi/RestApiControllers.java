package com.kindsonthegenius.fleetmsv2.assetm.controllers.restapi;

import com.kindsonthegenius.fleetmsv2.assetm.models.Asset;
import com.kindsonthegenius.fleetmsv2.assetm.services.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class RestApiControllers {
    private AssetService assetService;

    public void AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    public RestApiControllers(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/assets")
    public ResponseEntity<Asset> addNew(@RequestBody Asset asset) {
        // Perform validation on the incoming asset if needed
        if (asset == null) {
            return ResponseEntity.badRequest().build();
        }

        // Save the asset
        Asset savedAsset = assetService.save(asset);

        // Check if the save operation was successful
        if (savedAsset == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return a response with the saved asset and HTTP status CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsset);
    }

}
