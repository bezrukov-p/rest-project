package com.company.controller;

import com.company.exception.ResourceNotFoundException;
import com.company.model.Customer;
import com.company.repository.CustomersRepository;
import com.company.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class CustomersController {

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    MainService mainService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Customer customer = customersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer not found for id: " + id)
        );

        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer){
        return customersRepository.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        customersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer not found for id: " + id));
        customersRepository.deleteById(id);

        return ResponseEntity.ok("deleted");
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = customersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer not found for id: " + id));

        customer.setLastname(customerDetails.getLastname());
        customer.setDiscount(customerDetails.getDiscount());
        customer.setDistrict(customerDetails.getDistrict());

        final Customer customerUpdated = customersRepository.save(customer);

        return ResponseEntity.ok(customerUpdated);
    }

    @PatchMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomerPartially(@PathVariable(value = "id") Integer id,
                                                            @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = customersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer not found for id: " + id));

        if (customerDetails.getLastname() != null)
            customer.setLastname(customerDetails.getLastname());
        if (customerDetails.getDiscount() != null)
            customer.setDiscount(customerDetails.getDiscount());
        if (customerDetails.getDistrict() != null)
            customer.setDistrict(customerDetails.getDistrict());

        final Customer customerUpdated = customersRepository.save(customer);

        return ResponseEntity.ok(customerUpdated);
    }



    @GetMapping("/customers/different-districts")
    public List<String> getDifferentCustomersDistricts() {
        return customersRepository.getDifferentCustomersDistricts();
    }

    @GetMapping("/customers/lastname-and-discount-customers-lives-in-district")
    public List<List<String>> getLastnameAndDiscountLivesInDistrict(@RequestParam String district) {
        return mainService.getLastnameAndDiscountLivesInDistrict(district);
    }

}
