package com.example.TrimbleCars.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TrimbleCars.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(String status);
    List<Car> findByCarOwnerId(Long ownerId);
}
