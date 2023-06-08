package com.khiemtran.my_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khiemtran.my_project.dto.CustomerDto;
import com.khiemtran.my_project.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	@InjectMocks
	private CustomerController customerController;
	private final CustomerService customerService = mock(CustomerService.class);
	private CustomerDto customerDto = mock(CustomerDto.class);
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void init() {
		ReflectionTestUtils.setField(customerController, "customerService", customerService);
		customerDto = CustomerDto.builder()
		.id(1L)
		.age(1)
		.city("test")
		.email("test@gmail.com")
		.number(123456)
		.firstName("test")
		.lastName("test")
		.build();
	}

	@Test
	void createCustomer() throws Exception {
		Mockito.when(customerService.save(customerDto)).thenReturn(customerDto);
		ResultActions response = mockMvc.perform(post("/api/v1/customer")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(customerDto)));
		response.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void getAllCustomers() throws Exception {
		CustomerDto customerDto1 = CustomerDto.builder()
		.age(2)
		.city("test1")
		.email("test1@gmail.com")
		.number(1123456)
		.firstName("test1")
		.lastName("test1")
		.build();
		List<CustomerDto> list = new ArrayList<>(Arrays.asList(customerDto));
		list.add(customerDto1);
		when(customerService.getAll()).thenReturn(list);
		ResultActions response = mockMvc.perform(get("/api/v1/customer"));
		response.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void updateCustomer() throws Exception {
		byte[] requestBody = objectMapper.writeValueAsBytes(customerDto);
		// Make a PUT request to the endpoint being tested
		Long id = 1L;
		when(customerService.update(any(), customerDto)).thenReturn(customerDto);
		mockMvc.perform(put("/api/v1/customer/{id}", id)
		.contentType(MediaType.APPLICATION_JSON)
		.content(requestBody));
	}

	@Test
	void deleteCustomer() throws Exception {
		Long id = 1L;
		when(customerService.delete(id)).thenReturn(customerDto);
		ResultActions response = mockMvc.perform(delete("/api/v1/customer/{id}", id));
	}
}