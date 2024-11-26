package com.example.TrimbleCars.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TrimbleCars.entity.CarOwner;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
}

