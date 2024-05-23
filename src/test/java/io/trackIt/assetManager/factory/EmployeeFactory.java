package io.trackIt.assetManager.factory;

import io.trackIt.assetManager.model.Employee;
import com.github.javafaker.Faker;

public class EmployeeFactory {
    private static final Faker faker = new Faker();

    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setName(faker.name().fullName());
        return employee;
    }

}
