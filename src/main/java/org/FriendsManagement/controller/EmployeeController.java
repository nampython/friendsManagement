package org.FriendsManagement.controller;

import org.FriendsManagement.model.Employee;
import org.FriendsManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EmployeeController {
    public EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/api/v1/employees", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<Employee> getAllEmployeeFlux() {
        return employeeService.getAllOfEmployees();
    }

}
