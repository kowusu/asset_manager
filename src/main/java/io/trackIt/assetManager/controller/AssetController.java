package io.trackIt.assetManager.controller;

import io.trackIt.assetManager.model.Asset;
import io.trackIt.assetManager.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        return assetService.getAssetById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
        return assetService.createAsset(asset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset assetDetails) {
        return ResponseEntity.ok(assetService.updateAsset(id, assetDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unassigned")
    public List<Asset> getUnassignedAssets() {
        return assetService.getUnassignedAssets();
    }

}
