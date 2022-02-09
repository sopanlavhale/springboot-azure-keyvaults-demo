package com.azure.keyvaluts.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataValues {

	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String password;
	private String accountNumber;
	private String pin;
	private String cardNumber;
}
