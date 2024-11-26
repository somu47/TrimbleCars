package com.example.TrimbleCars.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

	@Override
	public String toString() {
		return "Lease [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", car=" + car + ", customer="
				+ customer + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Lease(Long id, LocalDate startDate, LocalDate endDate, Car car, Customer customer) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.car = car;
		this.customer = customer;
	}

	public Lease() {
		super();
		// TODO Auto-generated constructor stub
	}

   
}
