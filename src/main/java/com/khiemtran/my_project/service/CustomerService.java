package com.khiemtran.my_project.service;

import com.khiemtran.my_project.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
	CustomerDto save(CustomerDto customerRequest);

	List<CustomerDto> getAll();

	CustomerDto update(Long id, CustomerDto customerDto);

	CustomerDto delete(Long id);

	void deleteAll();

	long count();

	CustomerDto alert(Long id);
}
