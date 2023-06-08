package com.khiemtran.my_project.controller;

import com.khiemtran.my_project.dto.CustomerDto;
import com.khiemtran.my_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
		CustomerDto customerResponse = customerService.save(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(customerResponse);
	}

	@GetMapping("/customer")
	public ResponseEntity<?> getAllCustomers() {
		List<CustomerDto> listOfCustomerResponse = customerService.getAll();
		return ResponseEntity.status(HttpStatus.OK.value()).body(listOfCustomerResponse);
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable(name = "id") Long id,
	                                        @RequestBody CustomerDto customerDto) {
		CustomerDto customerResponse = customerService.update(id, customerDto);
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(customerResponse);
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable(name = "id") Long id) {
		CustomerDto customerDto = customerService.delete(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body("Delete Successfully." + customerDto);
	}

	@DeleteMapping("/customer/all")
	public ResponseEntity<?> clearAllCustomers() {
		customerService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("Cleared all successfully.");
	}

	@GetMapping("/customer/count")
	public ResponseEntity<?> countCustomers() {
		long numberCustomer = customerService.count();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(numberCustomer);
	}

	@GetMapping("/customer/check/{id}")
	public ResponseEntity<?> verifyId(@PathVariable(name = "id") Long id) {
		CustomerDto customerDto = customerService.alert(id);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}
}
