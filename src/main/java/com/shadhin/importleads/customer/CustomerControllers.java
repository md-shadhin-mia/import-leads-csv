package com.shadhin.importleads.customer;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerControllers {

    private final CustomerService customerService;
    public CustomerControllers(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        Page<Customer> customers = customerService.findAll(page, limit);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/count")
    public Long getCustomerCount() {
        return customerService.getCustomerCount();
    }
}
