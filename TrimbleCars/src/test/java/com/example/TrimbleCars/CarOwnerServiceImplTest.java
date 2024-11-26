package com.example.TrimbleCars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.CarOwner;
import com.example.TrimbleCars.repository.CarOwnerRepository;
import com.example.TrimbleCars.repository.CarRepository;
import com.example.TrimbleCars.services.CarOwnerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CarOwnerServiceImplTest {

	@Mock
	private CarOwnerRepository carOwnerRepository;

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarOwnerServiceImpl carOwnerService;

	private CarOwner carOwner;
	private Car car;

	@BeforeEach
	void setUp() {
		carOwner = new CarOwner();
		carOwner.setId(1L);
		carOwner.setName("John Doe");

		car = new Car();
		car.setId(1L);
		car.setModel("Tesla Model S");
		car.setStatus("Ideal");
	}

	@Test
	void registerCarOwner_ShouldReturnCarOwner_WhenOwnerIsRegistered() {
		when(carOwnerRepository.save(carOwner)).thenReturn(carOwner);

		CarOwner result = carOwnerService.registerCarOwner(carOwner);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(carOwnerRepository, times(1)).save(carOwner);
	}

	@Test
	void addCar_ShouldReturnCar_WhenCarIsAddedToOwner() {
		// Given
		when(carOwnerRepository.findById(1L)).thenReturn(Optional.of(carOwner));
		when(carRepository.save(car)).thenReturn(car);

		// When
		Car result = carOwnerService.addCar(1L, car);

		// Then
		assertNotNull(result);
		assertEquals("Tesla Model S", result.getModel());
		assertEquals("Ideal", result.getStatus());
		verify(carRepository, times(1)).save(car);
	}

	@Test
	void addCar_ShouldThrowRuntimeException_WhenOwnerNotFound() {
		when(carOwnerRepository.findById(1L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			carOwnerService.addCar(1L, car);
		});
		assertEquals("Owner not found", exception.getMessage());
		verify(carRepository, times(0)).save(car);
	}

	@Test
	void getCarsByOwner_ShouldReturnListOfCars_WhenCarsExist() {

		when(carRepository.findByCarOwnerId(1L)).thenReturn(Arrays.asList(car));

		var result = carOwnerService.getCarsByOwner(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Tesla Model S", result.get(0).getModel());
		verify(carRepository, times(1)).findByCarOwnerId(1L);
	}

	@Test
	void getCarsByOwner_ShouldReturnEmptyList_WhenNoCarsExist() {
		when(carRepository.findByCarOwnerId(1L)).thenReturn(Arrays.asList());

		var result = carOwnerService.getCarsByOwner(1L);

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(carRepository, times(1)).findByCarOwnerId(1L);
	}

	@Test
	void getCarStatusByOwner_ShouldReturnCars_WhenOwnerHasCars() {
		when(carRepository.findByCarOwnerId(1L)).thenReturn(Arrays.asList(car));

		var result = carOwnerService.getCarStatusByOwner(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Ideal", result.get(0).getStatus());
		verify(carRepository, times(1)).findByCarOwnerId(1L);
	}
}
