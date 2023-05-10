package org.FriendsManagement.controller;

import org.FriendsManagement.model.Employee;
import org.FriendsManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
    public EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/api/v1/employees")
    public Flux<Employee> getAllEmployeeFlux() {
        return employeeService.findAllEmployees();
    }

    @GetMapping(value = "/api/v1/{id}")
    public Mono<Employee> getEmployeeById(@PathVariable String id) {
        return employeeService.findEmployeeById(id);
    }

    @PostMapping(value = "/api/v1/update")
    public Mono<Employee> updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }
}
