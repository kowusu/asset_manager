package io.trackIt.assetManager.factory;

import io.trackIt.assetManager.model.Asset;
import com.github.javafaker.Faker;

public class AssetFactory {
    private static final Faker faker = new Faker();

    public static Asset createAsset() {
        Asset asset = new Asset();
        asset.setName(faker.commerce().productName());
        asset.setDescription(faker.lorem().sentence());
        return asset;
    }
}
