package com.example.TrimbleCars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TrimbleCars.entity.Car;
import com.example.TrimbleCars.entity.Customer;
import com.example.TrimbleCars.entity.Lease;
import com.example.TrimbleCars.repository.CarRepository;
import com.example.TrimbleCars.repository.CustomerRepository;
import com.example.TrimbleCars.repository.LeaseRepository;
import com.example.TrimbleCars.services.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CarRepository carRepository;

	@Mock
	private LeaseRepository leaseRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private Customer customer;
	private Car car;
	private Lease lease;

	@BeforeEach
	void setUp() {
		customer = new Customer();
		customer.setId(1L);
		customer.setName("John Doe");

		car = new Car();
		car.setId(1L);
		car.setModel("Tesla Model S");
		car.setStatus("Ideal");

		lease = new Lease();
		lease.setId(1L);
		lease.setStartDate(LocalDate.now());
		lease.setCar(car);
		lease.setCustomer(customer);
	}

	@Test
	void registerCustomer_ShouldReturnCustomer_WhenCustomerIsRegistered() {
		when(customerRepository.save(customer)).thenReturn(customer);

		Customer result = customerService.registerCustomer(customer);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(customerRepository, times(1)).save(customer);
	}

	@Test
	void getAvailableCars_ShouldReturnListOfCars_WhenCarsAreAvailable() {
		when(carRepository.findByStatus("Ideal")).thenReturn(Arrays.asList(car));

		var result = customerService.getAvailableCars();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Tesla Model S", result.get(0).getModel());
		verify(carRepository, times(1)).findByStatus("Ideal");
	}

	@Test
	void startLease_ShouldStartLease_WhenCarIsAvailable() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(carRepository.findById(1L)).thenReturn(Optional.of(car));
		when(leaseRepository.countByCustomerAndEndDateIsNull(customer)).thenReturn(0L);
		when(leaseRepository.save(any(Lease.class))).thenReturn(lease);

		Lease result = customerService.startLease(1L, 1L);

		assertNotNull(result);
		assertEquals("On Lease", car.getStatus());
		assertEquals(customer, result.getCustomer());
		verify(leaseRepository, times(1)).save(any(Lease.class));
		verify(carRepository, times(1)).save(car);
	}

	@Test
	void startLease_ShouldThrowRuntimeException_WhenCarIsNotAvailable() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(carRepository.findById(1L)).thenReturn(Optional.of(car));
		car.setStatus("On Lease");

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			customerService.startLease(1L, 1L);
		});
		assertEquals("Car is not available for lease", exception.getMessage());
		verify(leaseRepository, times(0)).save(any(Lease.class));
		verify(carRepository, times(0)).save(any(Car.class));
	}

	@Test
	void endLease_ShouldEndLease_WhenLeaseIsActive() {
		lease.setEndDate(null);
		when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));
		when(leaseRepository.save(lease)).thenReturn(lease);
		when(carRepository.save(car)).thenReturn(car);

		Lease result = customerService.endLease(1L, 1L);

		assertNotNull(result);
		assertEquals("Ideal", car.getStatus());
		assertNotNull(result.getEndDate());
		verify(leaseRepository, times(1)).save(lease);
		verify(carRepository, times(1)).save(car);
	}

	@Test
	void endLease_ShouldThrowRuntimeException_WhenLeaseNotFound() {
		when(leaseRepository.findById(1L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			customerService.endLease(1L, 1L);
		});
		assertEquals("Lease not found", exception.getMessage());
		verify(leaseRepository, times(0)).save(any(Lease.class));
		verify(carRepository, times(0)).save(any(Car.class));
	}

	@Test
	void getLeaseHistory_ShouldReturnLeaseHistory_WhenHistoryExists() {
		when(leaseRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(lease));

		var result = customerService.getLeaseHistory(1L);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getCustomer().getId());
		verify(leaseRepository, times(1)).findByCustomerId(1L);
	}
}
