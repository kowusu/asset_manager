package io.trackIt.assetManager.service;

import io.trackIt.assetManager.model.Asset;
import io.trackIt.assetManager.model.Employee;
import io.trackIt.assetManager.repository.AssetRepository;
import io.trackIt.assetManager.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AssetServiceTest {

    @InjectMocks
    private AssetService assetService;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAssets(){
        Asset asset1 = new Asset();
        Asset asset2 = new Asset();
        List<Asset> assets = Arrays.asList(asset1, asset2);

        when(assetRepository.findAll()).thenReturn(assets);

        List<Asset> result = assetService.getAllAssets();

        assertEquals(2, result.size());
        verify(assetRepository, times(1)).findAll();
    }

    @Test
    void getAssetById() {
        Asset asset = new Asset();
        asset.setId(1L);

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        Optional<Asset> result = assetService.getAssetById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(assetRepository, times(1)).findById(1L);
    }

    @Test
    void createAssert() {
        Asset asset = new Asset();

        when(assetRepository.save(asset)).thenReturn(asset);

        Asset result = assetService.createAsset(asset);

        assertNotNull(result);
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void updateAsset() {
        Asset existingAsset = new Asset();
        existingAsset.setId(1L);
        Asset updatedAsset = new Asset();
        updatedAsset.setName("MacBook Pro 123");
        updatedAsset.setDescription("Macbook Pro model 2024");

        when(assetRepository.findById(1L)).thenReturn(Optional.of(existingAsset));
        when(assetRepository.save(existingAsset)).thenReturn(existingAsset);

        Asset result = assetService.updateAsset(1L, updatedAsset);

        assertNotNull(result);
        assertEquals("MacBook Pro 123", result.getName());
        assertEquals("Macbook Pro model 2024", result.getDescription());
    }

    @Test
    void assignAssetToEmployee() {
        Asset existingAsset = new Asset();
        existingAsset.setId(1L);
        Employee employee = new Employee();
        employee.setId(1L);

        when(assetRepository.findById(1L)).thenReturn(Optional.of(existingAsset));
        when(assetRepository.save(existingAsset)).thenReturn(existingAsset);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Asset result = assetService.assignAssetToEmployee(1L, 1L);

        assertNotNull(result);
        assertEquals(result.getEmployee(), employee);
    }

    @Test
    void deleteAsset() {
        Asset asset = new Asset();
        asset.setId(1L);

        doNothing().when(assetRepository).deleteById(1L);

        assetService.deleteAsset(1L);

        verify(assetRepository, times(1)).deleteById(1L);
    }

    @Test
    void getUnassignedAssets() {
        Asset asset1 = new Asset();
        Asset asset2 = new Asset();
        List<Asset> assets = Arrays.asList(asset1, asset2);

        when(assetRepository.findByEmployeeIsNull()).thenReturn(assets);

        List<Asset> result = assetService.getUnassignedAssets();

        assertEquals(2, result.size());
        verify(assetRepository, times(1)).findByEmployeeIsNull();
    }
}
