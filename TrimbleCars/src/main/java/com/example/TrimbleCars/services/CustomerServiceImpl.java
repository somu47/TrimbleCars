package com.example.TrimbleCars.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TrimbleCars.Exception.CarNotFoundException;
import com.example.TrimbleCars.Exception.CustomerNotFoundException;
import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;
import com.example.TrimbleCars.repository.CarRepository;
import com.example.TrimbleCars.repository.CustomerRepository;
import com.example.TrimbleCars.repository.LeaseRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private LeaseRepository leaseRepository;

	@Override
	public Customer registerCustomer(Customer customer) {
		logger.info("Registering customer:", customer.getName());
		return customerRepository.save(customer);
	}

	@Override
	public List<Car> getAvailableCars() {
		logger.info("Available Cars:", carRepository.findByStatus("Ideal"));

		return carRepository.findByStatus("Ideal");
	}

	@Override
	public Lease startLease(Long customerId, Long carId) {
		// Fetch customer and car from repositories
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not found Exception"));
		if (!car.getStatus().equals("Ideal")) {
			throw new RuntimeException("Car is not available for lease");
		}
		long activeLeasesCount = leaseRepository.countByCustomerAndEndDateIsNull(customer);
		if (activeLeasesCount >= 2) {
			throw new RuntimeException("Cannot have more than 2 active leases at a time");
		}
		Lease lease = new Lease();
		lease.setStartDate(LocalDate.now());
		lease.setCar(car);
		lease.setCustomer(customer);
		car.setStatus("On Lease");
		carRepository.save(car);
		
	    logger.info("Lease started successfully for customer ID: {} and car ID: {}", customerId, carId);

		return leaseRepository.save(lease);
	}

	@Override
	public Lease endLease(Long customerId, Long leaseId) {
		Lease lease = leaseRepository.findById(leaseId).orElseThrow(() -> new RuntimeException("Lease not found"));

		lease.setEndDate(LocalDate.now());
		lease.getCar().setStatus("Ideal");
		carRepository.save(lease.getCar());

		return leaseRepository.save(lease);
	}

	@Override
	public List<Lease> getLeaseHistory(Long customerId) {

		return leaseRepository.findByCustomerId(customerId);
	}
}
