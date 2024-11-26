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
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.services.CarOwnerService;

@RestController
@RequestMapping("/api/car-owners")
public class CarOwnerController {

    @Autowired
    private CarOwnerService carOwnerService;

    @PostMapping("/register")
    public ResponseEntity<CarOwner> registerCarOwner(@RequestBody CarOwner carOwner) {
        return ResponseEntity.ok(carOwnerService.registerCarOwner(carOwner));
    }

    @PostMapping("/{ownerId}/cars")
    public ResponseEntity<Car> addCar(@PathVariable Long ownerId, @RequestBody Car car) {
        return ResponseEntity.ok(carOwnerService.addCar(ownerId, car));
    }

    @GetMapping("/{ownerId}/cars")
    public ResponseEntity<List<Car>> getCarsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(carOwnerService.getCarsByOwner(ownerId));
    }

    @GetMapping("/{ownerId}/cars/status")
    public ResponseEntity<List<Car>> getCarStatusByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(carOwnerService.getCarStatusByOwner(ownerId));
    }
}
