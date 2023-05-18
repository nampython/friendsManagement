package org.FriendsManagement.controller;

import org.FriendsManagement.model.Customer;
import org.FriendsManagement.model.ResponseObject;
import org.FriendsManagement.respository.CustomerDao;
import org.FriendsManagement.respository.CustomerDaoImpl;
import org.FriendsManagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @GetMapping(value = "/customers/send/promo/list")
    public List<String> processCustomer() {

        return customerService.sendPromoToCustomer();
    }

    @GetMapping(value = "/customers/send/promo/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> processCustomerStream() {
        return customerService.sendPromoToCustomerStream();
    }


    @GetMapping(value = "/customers/find", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseObject>> findCustomerById(@RequestParam String id) {

        Mono<Customer> customerMono = customerDao.loadCustomerById(id);

        return customerMono
                .flatMap(lt -> {
                    Customer customer = customerMono.block();
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setStatus(200);
                    responseObject.setData(customer);
                    return Mono.just(ResponseEntity.ok().body(responseObject));
                }).onErrorResume(throwable -> {
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setStatus(200);
                    responseObject.setError(throwable.getMessage());
                    return Mono.just(ResponseEntity.ok().body(responseObject));
                });
    }

    @PostMapping(value = "/customers/save", consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ResponseObject>> saveCustomer(@RequestBody Mono<Customer> customer) {
        return customerDao.saveCustomer(customer)
                .flatMap(cus -> {
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setStatus(200);
                    responseObject.setData(cus);
                    return Mono.just(ResponseEntity.ok().body(responseObject));
                }).onErrorResume(
                        throwable -> {
                            ResponseObject responseObject = new ResponseObject();
                            responseObject.setStatus(200);
                            responseObject.setError(throwable.getMessage());
                            return Mono.just(ResponseEntity.ok().body(responseObject));
                        }
                );
    }
}


