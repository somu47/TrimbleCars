package com.example.TrimbleCars.services;


import java.util.List;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    List<Car> getAvailableCars();
    Lease startLease(Long customerId, Long carId);
    Lease endLease(Long customerId, Long leaseId);
    List<Lease> getLeaseHistory(Long customerId);
}

