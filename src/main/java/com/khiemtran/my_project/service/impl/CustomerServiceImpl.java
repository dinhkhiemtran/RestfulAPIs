package com.khiemtran.my_project.service.impl;

import com.khiemtran.my_project.dto.CustomerDto;
import com.khiemtran.my_project.exception.ResourceNotFoundException;
import com.khiemtran.my_project.model.Customer;
import com.khiemtran.my_project.repository.CustomerRepository;
import com.khiemtran.my_project.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerDto save(CustomerDto customerRequest) {
		Customer customer = customerRequest.toEntity();
		customerRepository.save(customer);
		log.info("Customer has been saved successfully.");
		return customer.toDto();
	}

	@Override
	public List<CustomerDto> getAll() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> listCustomerResponse = customers.stream().map(customer -> customer.toDto()).collect(Collectors.toList());
		log.info("Get all customers");
		return listCustomerResponse;
	}

	@Override
	public CustomerDto update(Long id, CustomerDto customerDto) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
		customer.setFirstName(customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName());
		customer.setAge(customerDto.getAge());
		customer.setCity(customerDto.getCity());
		customer.setNumber(customerDto.getNumber());
		CustomerDto customerResponse = customer.toDto();
		save(customerResponse);
		log.info("Customer has been updated successfully.");
		return customerResponse;
	}

	@Override
	public CustomerDto delete(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
		CustomerDto customerResponse = customer.toDto();
		customerRepository.delete(customer);
		log.info("Customer has been deleted successfully.");
		return customerResponse;
	}

	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
		log.info("Deleted all");
	}

	@Override
	public long count() {
		log.info("Count all customers.");
		return customerRepository.count();
	}

	private boolean existsById(Long id) {
		boolean isCustomerExist = customerRepository.existsById(id);
		return isCustomerExist;
	}

	@Override
	public CustomerDto alert(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (existsById(id)) {
			log.info("Get customer: \n" + customer.get());
			return customer.get().toDto();
		}
		log.info("Customer has not exist.");
		return null;
	}
}
