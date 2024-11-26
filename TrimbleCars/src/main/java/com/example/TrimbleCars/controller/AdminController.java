package com.example.TrimbleCars.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;
import com.example.TrimbleCars.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/car-owners")
    public ResponseEntity<List<CarOwner>> getAllCarOwners() {
        return ResponseEntity.ok(adminService.getAllCarOwners());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(adminService.getAllCustomers());
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @GetMapping("/leases")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return ResponseEntity.ok(adminService.getAllLeases());
    }
}

