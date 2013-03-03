/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Component
public class ClientValidator {
	public void validateAddClientGeneralForm(Client client, Errors errors) {
		System.out.println("Walidacja general form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "client.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "client.password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "client.firstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "client.lastName.required");

	}
	
	public void validateAddClientAddressForm(Client client, Errors errors) {
		System.out.println("Walidacja address form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.street", "client.address.street.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "client.address.city.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "client.address.postalCode.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "client.phoneNumber.required");

	}
}
