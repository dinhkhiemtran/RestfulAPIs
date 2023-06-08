package com.khiemtran.my_project.dto;

import com.khiemtran.my_project.model.Customer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
	private Long id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	@NotNull
	private String email;
	@NotEmpty
	private int age;
	@NotBlank
	@Size(min = 1, max = 10)
	private int number;
	@NotEmpty
	private String city;

	public Customer toEntity() {
		Customer customer = Customer.builder()
		.id(id)
		.firstName(firstName)
		.lastName(lastName)
		.email(email)
		.age(age)
		.number(number)
		.city(city)
		.build();
		return customer;
	}
}
