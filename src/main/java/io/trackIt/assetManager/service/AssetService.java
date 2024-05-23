package io.trackIt.assetManager.service;

import io.trackIt.assetManager.model.Asset;
import io.trackIt.assetManager.model.Employee;
import io.trackIt.assetManager.repository.AssetRepository;
import io.trackIt.assetManager.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(Long id){
        return assetRepository.findById(id);
    }

    public Asset createAsset(Asset asset){
        return assetRepository.save(asset);
    }

    public Asset updateAsset(Long id, Asset assetDetails){
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setName((assetDetails.getName()));
        asset.setDescription(assetDetails.getDescription());
        return assetRepository.save(asset);
    }

    public Asset assignAssetToEmployee(Long assetId, Long employeeId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found."));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found."));

        asset.setEmployee(employee);
        return assetRepository.save(asset);
    }

    public void deleteAsset(Long id){
        assetRepository.deleteById(id);
    }

    public List<Asset> getUnassignedAssets() {
        return assetRepository.findByEmployeeIsNull();
    }
}