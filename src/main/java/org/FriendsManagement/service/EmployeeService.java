package org.FriendsManagement.service;

import org.FriendsManagement.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EmployeeService {
    Flux<Employee> findAllEmployees();

    Mono<Employee> findEmployeeById(String id);
    Mono<Employee> updateEmployee(Employee employee);
}
