package com.azure.keyvaluts.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class SymmetricUser {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String password;
	private String accountNumber;
	private String pin;
	private String cardNumber;
	
}
