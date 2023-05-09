package org.FriendsManagement.service;

import com.github.javafaker.Faker;
import org.FriendsManagement.model.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    public Flux<Employee> getAllOfEmployees() {
        Faker faker = new Faker();
        return Flux.generate((
                SynchronousSink<Employee> sink) ->
                sink.next(new Employee(faker.food().hashCode(), faker.address().cityName()))
        ).delayElements(Duration.ofSeconds(1L)
        );
    }
}
