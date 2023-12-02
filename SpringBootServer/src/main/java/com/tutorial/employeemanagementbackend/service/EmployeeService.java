package com.tutorial.employeemanagementbackend.service;

import com.tutorial.employeemanagementbackend.model.Employee;
import com.tutorial.employeemanagementbackend.respository.EmployeeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService implements  EmployeeServiceInterface{

    @Autowired
    private EmployeeRespository employeeRespository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRespository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRespository.findById(id);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRespository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public Employee updateEmployee(int id, Employee employee) {
        Employee employeeToUpdate=employeeRespository.findById(id).orElseThrow();
        employeeToUpdate.setFirstName(employee.getFirstName());
        employeeToUpdate.setLastName(employee.getLastName());
        employeeToUpdate.setEmail(employee.getEmail());

        return employeeRespository.save(employeeToUpdate);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRespository.deleteById(id);
    }
}
