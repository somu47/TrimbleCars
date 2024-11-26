package com.example.TrimbleCars.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.repository.CarOwnerRepository;
import com.example.TrimbleCars.repository.CarRepository;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

	private static final Logger logger = LogManager.getLogger(CarOwnerServiceImpl.class);

	@Autowired
	private CarOwnerRepository carOwnerRepository;

	@Autowired
	private CarRepository carRepository;

	@Override
	public CarOwner registerCarOwner(CarOwner carOwner) {
	    logger.info("Attempting to register car owner with name", carOwner.getName());
	    CarOwner savedCarOwner = carOwnerRepository.save(carOwner);
	    logger.info("Successfully registered car owner with ID", savedCarOwner.getId());
		return savedCarOwner;
	}

	@Override
	public Car addCar(Long ownerId, Car car) {
		 CarOwner carOwner = carOwnerRepository.findById(ownerId)
		            .orElseThrow(() -> {
		                logger.error("Car owner with ID not found", ownerId);
		                return new RuntimeException("Owner not found");
		            });
		car.setCarOwner(carOwner);
		car.setStatus("Ideal");
		return carRepository.save(car);
	}

	@Override
	public List<Car> getCarsByOwner(Long ownerId) {
	    logger.info("Fetching cars for owner ID", ownerId);
		return carRepository.findByCarOwnerId(ownerId);
	}

	@Override
	public List<Car> getCarStatusByOwner(Long ownerId) {
	    logger.info("Fetching car statuses for owner ID:", ownerId);

	    List<Car> cars = carRepository.findByCarOwnerId(ownerId);

	    if (cars.isEmpty()) {
	        logger.warn("No cars found for owner ID:", ownerId);
	    } else {
	        logger.info("Found {} cars for owner ID:", cars.size(), ownerId);
	    }

	    return cars;
	}

}
