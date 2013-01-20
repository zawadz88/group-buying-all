package pl.edu.pw.eiti.groupbuying.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Component
public class ClientValidator {
	public void validateAddClientGeneralForm(Client client, Errors errors) {
		System.out.println("Walidacja general form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "client.username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "client.password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "client.firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "client.lastName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "client.email.required");

	}
	
	public void validateAddClientAddressForm(Client client, Errors errors) {
		System.out.println("Walidacja address form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.street", "client.address.street.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "client.address.city.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "client.address.postalCode.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "client.phoneNumber.required");

	}
}
