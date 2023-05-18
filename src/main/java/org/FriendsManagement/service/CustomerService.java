package org.FriendsManagement.service;

import org.FriendsManagement.model.Customer;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public interface CustomerService {
    List<String> sendPromoToCustomer();
    Flux<String> sendPromoToCustomerStream();
}
