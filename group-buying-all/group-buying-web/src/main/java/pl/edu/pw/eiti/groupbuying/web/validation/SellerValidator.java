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

import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

@Component
public class SellerValidator {
	public void validateAddSellerGeneralForm(Seller seller, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "seller.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "seller.password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "seller.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "trade", "seller.trade.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nip", "seller.nip.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "seller.description.required");

	}
	
	public void validateAddSellerAddressForm(Seller seller, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.street", "seller.address.street.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "seller.address.city.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "seller.address.postalCode.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "seller.phoneNumber.required");

	}
}
