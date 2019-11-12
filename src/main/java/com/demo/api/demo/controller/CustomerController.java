package com.demo.api.demo.controller;

import com.demo.api.demo.model.Customer;
import com.demo.api.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id)
        throws Exception {
        Customer customer =
                customerRepository
                    .findById(id)
                    .orElseThrow(() -> new Exception("User was not found with id: " + id));
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable(value = "email") String email)
            throws Exception {
        Customer customer =
                customerRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new Exception("User was not found with email: " + email));
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteUserById(@PathVariable(name="id") Long id) throws Exception {
        Customer customer=
                customerRepository
                .findById(id)
                .orElseThrow(() -> new Exception("User was not found with id: " + id));

        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @DeleteMapping("/customers/email/{email}")
    public Map<String, Boolean> deleteUserByEmail(@PathVariable(name="email") String email) throws Exception {
        Customer customer=
                customerRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new Exception("User was not found with email: " + email));

        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
