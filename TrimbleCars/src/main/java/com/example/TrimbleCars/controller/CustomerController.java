package com.example.TrimbleCars.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;
import com.example.TrimbleCars.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAvailableCars() {
        return ResponseEntity.ok(customerService.getAvailableCars());
    }

    @PostMapping("/{customerId}/lease")
    public ResponseEntity<Lease> startLease(@PathVariable Long customerId, @RequestBody Long carId) {
        return ResponseEntity.ok(customerService.startLease(customerId, carId));
    }

    @PostMapping("/{customerId}/lease/{leaseId}/end")
    public ResponseEntity<Lease> endLease(@PathVariable Long customerId, @PathVariable Long leaseId) {
        return ResponseEntity.ok(customerService.endLease(customerId, leaseId));
    }

    @GetMapping("/{customerId}/lease-history")
    public ResponseEntity<List<Lease>> getLeaseHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getLeaseHistory(customerId));
    }
}
