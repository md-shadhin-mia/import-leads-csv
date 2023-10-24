package com.shadhin.importleads.customer;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> findAll(int page, int limit) {
        return customerRepository.findAll(PageRequest.of(page, limit));
    }

    public Long getCustomerCount() {
        return customerRepository.count();
    }
}
