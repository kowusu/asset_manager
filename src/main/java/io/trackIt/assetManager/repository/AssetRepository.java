package io.trackIt.assetManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.trackIt.assetManager.model.Asset;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByEmployeeIsNull();
}
