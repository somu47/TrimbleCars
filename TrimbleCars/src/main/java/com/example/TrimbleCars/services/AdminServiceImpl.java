package com.example.TrimbleCars.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;
import com.example.TrimbleCars.repository.CarOwnerRepository;
import com.example.TrimbleCars.repository.CarRepository;
import com.example.TrimbleCars.repository.CustomerRepository;
import com.example.TrimbleCars.repository.LeaseRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

	@Autowired
	private CarOwnerRepository carOwnerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private LeaseRepository leaseRepository;

	@Override
	public List<CarOwner> getAllCarOwners() {
	    logger.info("Fetching all car owners");

	    List<CarOwner> carOwners = carOwnerRepository.findAll();

	    if (carOwners.isEmpty()) {
	        logger.warn("No car owners found.");
	    } else {
	        logger.info("Found car owners", carOwners.size());
	    }

	    return carOwners;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}

	@Override
	public List<Lease> getAllLeases() {
	    logger.info("Fetching all leases");

	    List<Lease> leases = leaseRepository.findAll();

	    if (leases.isEmpty()) {
	        logger.warn("No leases found.");
	    } else {
	        logger.info("Found leases.", leases.size());
	    }

	    return leases;
	}

}
