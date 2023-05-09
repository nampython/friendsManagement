package org.FriendsManagement.service;

import org.FriendsManagement.model.Employee;
import reactor.core.publisher.Flux;


public interface EmployeeService {
    Flux<Employee> getAllOfEmployees();
}
