package com.example.TrimbleCars.services;


import java.util.List;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;

public interface AdminService {
    List<CarOwner> getAllCarOwners();
    List<Customer> getAllCustomers();
    List<Car> getAllCars();
    List<Lease> getAllLeases();
}

