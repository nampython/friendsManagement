package org.FriendsManagement.repository;

import org.FriendsManagement.model.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    private static final List<String> customerRecords = new ArrayList();

    static {
        List<String> users = LongStream.range(1, 11)
                .mapToObj(it -> {
                    Long id = it;
                    String name = "Customer " + id;
                    String mail = "Customer " + id + "@gmail.com";
                    return String.join(",", String.valueOf(it), name, mail);
                }).collect(Collectors.toList());

        customerRecords.addAll(users);
    }


    private static void delayFetchingByMs(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Flux<String> getUsersFromIterable() {
        return Flux.fromIterable(customerRecords);
    }


    @Override
    public Flux<Customer> loadCustomersStream(boolean keepDelay) {
        return getUsersFromIterable()
                .doOnNext(it -> {
                    if (keepDelay) {
                        delayFetchingByMs(3000);
                    }
                    System.out.println("Fetching user: " + customerRecords.indexOf(it));
                })
                .map(CustomerDaoImpl::preparedCustomerFromCustomerRecord);
    }

    @Override
    public List<Customer> loadCustomers() {
        List<Customer> users = new ArrayList<>();
        for (int i = 0; i < customerRecords.size(); i++) {
            if (i == 7) {
                delayFetchingByMs(3000);
            }
            String user = customerRecords.get(i);
            System.out.println("Fetching user: " + user);
            Customer customer = preparedCustomerFromCustomerRecord(user);
            users.add(customer);
        }
        return users;
    }

    @Override
    public Mono<Customer> loadCustomerById(String email) {
        Optional<String> customerById = customerRecords
                .stream()
                .filter(lt -> lt.split(",")[2].equalsIgnoreCase(email))
                .findFirst();
        if (customerById.isPresent()) {
            return customerById.map(lt -> Mono.just(preparedCustomerFromCustomerRecord(lt))).get();
        }
        return Mono.error(new RuntimeException("Customer not found"));
    }

    private static Customer preparedCustomerFromCustomerRecord(String user) {
        String[] customerRecordArray = user.split(",");
        return new Customer(Long.parseLong(customerRecordArray[0]), customerRecordArray[1], customerRecordArray[2]);
    }

    @Override
    public Mono<Customer> saveCustomer(Mono<Customer> customer) {
        return customer.map(cus -> {
            customerRecords.add(cus.getName());
            return cus;
        });
    }
}