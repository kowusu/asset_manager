package io.trackIt.assetManager.repository;

import io.trackIt.assetManager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface EmployeeRepository {
    List<Employee> findByAssetsIsNull();
}
