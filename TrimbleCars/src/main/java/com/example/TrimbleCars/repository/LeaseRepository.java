package com.example.TrimbleCars.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomerId(Long customerId);
    List<Lease> findByCarId(Long carId);
	long countByCustomerAndEndDateIsNull(Customer customer);  
}
