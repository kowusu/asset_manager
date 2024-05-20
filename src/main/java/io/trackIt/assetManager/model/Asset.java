package io.trackIt.assetManager.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

}
