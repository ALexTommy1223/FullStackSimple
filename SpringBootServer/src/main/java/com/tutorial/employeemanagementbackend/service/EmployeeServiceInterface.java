package com.tutorial.employeemanagementbackend.service;

import com.tutorial.employeemanagementbackend.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceInterface {
    public Employee saveEmployee(Employee employee);
    public Optional<Employee> getEmployeeById(int id);
    public List<Employee> getAllEmployee();
    public Employee updateEmployee(int id , Employee employee);
    public void deleteEmployee(int id);
}
