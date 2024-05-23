package io.trackIt.assetManager.controller;

import io.trackIt.assetManager.factory.AssetFactory;
import io.trackIt.assetManager.factory.EmployeeFactory;
import io.trackIt.assetManager.model.Asset;
import io.trackIt.assetManager.model.Employee;
import io.trackIt.assetManager.service.AssetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssetController.class)
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Asset asset;
    private Employee employee;

    @BeforeEach
    void setUp() {
        asset = AssetFactory.createAsset();
        asset.setId(1L);

        employee = EmployeeFactory.createEmployee();
        employee.setId(1L);
    }


    @Test
    void getAllAssets() throws Exception {
        when(assetService.getAllAssets()).thenReturn(Arrays.asList(asset));

        mockMvc.perform(get("/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(asset.getName()))
                .andExpect(jsonPath("$[0].description").value(asset.getDescription()));
    }

    @Test
    void getAssetById() throws Exception {
        when(assetService.getAssetById(anyLong())).thenReturn(Optional.of(asset));

        mockMvc.perform(get("/assets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.description").value(asset.getDescription()));
    }

    @Test
    void createAsset() throws Exception {
        when(assetService.createAsset(any(Asset.class))).thenReturn(asset);

        mockMvc.perform(post("/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.description").value(asset.getDescription()));

    }

    @Test
    void updateAsset() throws Exception {
        when(assetService.updateAsset(anyLong(), any(Asset.class))).thenReturn(asset);

        mockMvc.perform(put("/assets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.description").value(asset.getDescription()));
    }

    @Test
    void assignAssetToEmployee() throws Exception {
        asset.setEmployee(employee);

        when(assetService.assignAssetToEmployee(anyLong(), anyLong())).thenReturn(asset);

        mockMvc.perform(put("/assets/1/assign/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.description").value(asset.getDescription()))
                .andExpect(jsonPath("$.employee.name").value(employee.getName()));
    }

    @Test
    void deleteAsset() throws Exception {
        mockMvc.perform(delete("/assets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUnassignedAssets() throws Exception {
        when(assetService.getUnassignedAssets()).thenReturn(Arrays.asList(asset));

        mockMvc.perform(get("/assets/unassigned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(asset.getName()))
                .andExpect(jsonPath("$[0].description").value(asset.getDescription()));
    }
}
