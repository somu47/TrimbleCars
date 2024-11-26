package com.example.TrimbleCars.services;


import java.util.List;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;

public interface CarOwnerService {
    CarOwner registerCarOwner(CarOwner carOwner);
    Car addCar(Long ownerId, Car car);
    List<Car> getCarsByOwner(Long ownerId);
    List<Car> getCarStatusByOwner(Long ownerId);
}

