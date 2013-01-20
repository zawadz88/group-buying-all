package pl.edu.pw.eiti.groupbuying.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

@Component
public class SellerValidator {
	public void validateAddSellerGeneralForm(Seller seller, Errors errors) {
		System.out.println("Walidacja general form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "seller.username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "seller.password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "seller.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "trade", "seller.trade.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "seller.description.required");

	}
	
	public void validateAddSellerAddressForm(Seller seller, Errors errors) {
		System.out.println("Walidacja address form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.street", "seller.address.street.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "seller.address.city.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "seller.address.postalCode.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "seller.phoneNumber.required");

	}
}
