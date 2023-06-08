package com.khiemtran.my_project.service.impl;

import com.khiemtran.my_project.dto.CustomerDto;
import com.khiemtran.my_project.exception.ResourceNotFoundException;
import com.khiemtran.my_project.model.Customer;
import com.khiemtran.my_project.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplTest {
	@InjectMocks
	private CustomerServiceImpl customerService;
	private CustomerRepository customerRepository = mock(CustomerRepository.class);
	private Customer customer = mock(Customer.class);
	private CustomerDto customerDto = mock(CustomerDto.class);
	private ResourceNotFoundException resourceNotFoundException = mock(ResourceNotFoundException.class);

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
//		ReflectionTestUtils.setField("customerService", "customerRepository", customerRepository);
	}

	@Test
	public void saveTest() {
		when(customerDto.toEntity()).thenReturn(customer);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		when(customer.toDto()).thenReturn(customerDto);
		// Act
		CustomerDto customerResponse = customerService.save(customerDto);
		// Assert
		verify(customerRepository).save(any(Customer.class));
		assertEquals(customerDto, customerResponse);
	}

	@Test
	public void getAllTest() {
		// Arrange
		Customer customer1 = mock(Customer.class);
		Customer customer2 = mock(Customer.class);
		CustomerDto customerDto1 = mock(CustomerDto.class);
		CustomerDto customerDto2 = mock(CustomerDto.class);
		// Mock the behavior of the Repository and Customer objects
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
		when(customer1.toDto()).thenReturn(customerDto1);
		when(customer2.toDto()).thenReturn(customerDto2);
		// Act
		List<CustomerDto> customerListResponse = customerService.getAll();
		// Assert
		assertEquals(2, customerListResponse.size());
		assertEquals(customerDto1, customerListResponse.get(0));
		assertEquals(customerDto2, customerListResponse.get(1));
	}

	@Test
	public void testUpdate() {
		Long id = 1L;
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName("test");
		customer.setLastName("test");
		customer.setEmail("test@gmail.com");
		customer.setAge(25);
		customer.setCity("test");
		customer.setNumber(654321);
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(id);
		customerDto.setFirstName("test");
		customerDto.setLastName("test");
		customerDto.setEmail("test@gmail.com");
		customerDto.setAge(26);
		customerDto.setCity("test");
		customerDto.setNumber(654321);
		Customer updatedCustomer = new Customer();
		updatedCustomer.setId(id);
		updatedCustomer.setFirstName("test");
		updatedCustomer.setLastName("test");
		updatedCustomer.setEmail("test@gmail.com");
		updatedCustomer.setAge(26);
		updatedCustomer.setCity("test");
		updatedCustomer.setNumber(654321);
		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
		CustomerDto updatedCustomerDto = customerService.update(id, customerDto);
		verify(customerRepository, times(1)).findById(id);
		verify(customerRepository, times(1)).save(updatedCustomer);
		assertEquals(updatedCustomerDto.getFirstName(), customerDto.getFirstName());
		assertEquals(updatedCustomerDto.getLastName(), customerDto.getLastName());
		assertEquals(updatedCustomerDto.getAge(), customerDto.getAge());
		assertEquals(updatedCustomerDto.getCity(), customerDto.getCity());
		assertEquals(updatedCustomerDto.getNumber(), customerDto.getNumber());
	}

	@Test
	public void testDelete() {
		Long id = 1L;
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName("test");
		customer.setLastName("test");
		customer.setAge(25);
		customer.setCity("test");
		customer.setEmail("test@gmail.com");
		customer.setNumber(123456);
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(id);
		customerDto.setFirstName("test");
		customerDto.setLastName("test");
		customerDto.setAge(25);
		customerDto.setCity("test");
		customerDto.setEmail("test@gmail.com");
		customerDto.setNumber(123456);
		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		CustomerDto deletedCustomerDto = customerService.delete(id);
		verify(customerRepository, times(1)).findById(id);
		verify(customerRepository, times(1)).delete(customer);
		assertEquals(deletedCustomerDto.getId(), customerDto.getId());
		assertEquals(deletedCustomerDto.getFirstName(), customerDto.getFirstName());
		assertEquals(deletedCustomerDto.getLastName(), customerDto.getLastName());
		assertEquals(deletedCustomerDto.getAge(), customerDto.getAge());
		assertEquals(deletedCustomerDto.getCity(), customerDto.getCity());
		assertEquals(deletedCustomerDto.getNumber(), customerDto.getNumber());
	}
}
