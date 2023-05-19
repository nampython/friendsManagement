package org.FriendsManagement.service;

import org.FriendsManagement.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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
