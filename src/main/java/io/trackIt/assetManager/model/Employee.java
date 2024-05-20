package io.trackIt.assetManager.model;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "employee")
    private Set<Asset> assets;
}