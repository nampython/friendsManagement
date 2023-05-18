package org.FriendsManagement.service;

import org.FriendsManagement.model.Customer;
import org.FriendsManagement.model.ResponseObject;
import org.FriendsManagement.respository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> sendPromoToCustomer() {
        return customerDao.loadCustomers().stream()
                .map(cus -> {
                    String mail = cus.getMail();
                    return "Promos send to customer: " + mail;
                }).collect(Collectors.toList());
    }

    @Override
    public Flux<String> sendPromoToCustomerStream() {
        return customerDao.loadCustomersStream(true)
                .map(it -> "Promos send to customer: " + it.getMail());
    }
}
