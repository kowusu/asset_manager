package io.trackIt.assetManager.service;

import io.trackIt.assetManager.factory.EmployeeFactory;
import io.trackIt.assetManager.model.Employee;
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


public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees(){
        Employee employee1 = EmployeeFactory.createEmployee();
        Employee employee2 = EmployeeFactory.createEmployee();
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById() {
        Employee employee = EmployeeFactory.createEmployee();
        employee.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void createEmployee() {
        Employee employee = EmployeeFactory.createEmployee();

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployee() {
        Employee existingEmployee = EmployeeFactory.createEmployee();
        existingEmployee.setId(1L);
        Employee updatedEmployee = EmployeeFactory.createEmployee();
        updatedEmployee.setName("Bob Hope");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertNotNull(result);
        assertEquals("Bob Hope", result.getName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void deleteEmployee() {
        Employee employee = EmployeeFactory.createEmployee();
        employee.setId(1L);

        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void getEmployeesWithoutAssets() {
        Employee employee1 = EmployeeFactory.createEmployee();
        Employee employee2 = EmployeeFactory.createEmployee();
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findByAssetsIsNull()).thenReturn(employees);

        List<Employee> result = employeeService.getEmployeesWithoutAssets();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findByAssetsIsNull();
    }
}
