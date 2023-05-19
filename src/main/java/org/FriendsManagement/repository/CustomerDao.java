package org.FriendsManagement.repository;

import org.FriendsManagement.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerDao {
    Flux<Customer>  loadCustomersStream(boolean keepDelay);
    List<Customer> loadCustomers();
    Mono<Customer> loadCustomerById(String email);
    Mono<Customer> saveCustomer(Mono<Customer> customer);
}
