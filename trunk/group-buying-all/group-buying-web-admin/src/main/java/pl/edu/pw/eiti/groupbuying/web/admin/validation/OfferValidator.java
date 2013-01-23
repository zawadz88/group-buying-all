package pl.edu.pw.eiti.groupbuying.web.admin.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import pl.edu.pw.eiti.groupbuying.core.domain.Offer;

@Component
public class OfferValidator {
	public void validateAddOfferGeneralForm(Offer offer, Errors errors) {
		System.out.println("Walidacja general form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "offer.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "offer.description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "imageUrl", "offer.imageUrl.required");
		validateCategory(errors);
		validateState(errors);
	}

	private void validateState(Errors errors) {
		String field = "state";
	    Assert.notNull(errors, "Errors object must not be null");
	    Object value = errors.getFieldValue(field);//TODO lepsza walidacja
	    if ((value == null)) {
	    	errors.rejectValue(field, "offer.state.incorrect", null, null);
	    }
	}
	
	private void validateCategory(Errors errors) {
		String field = "category.categoryId";
	    Assert.notNull(errors, "Errors object must not be null");
	    Object value = errors.getFieldValue(field);
	    if ((value == null) || (!(StringUtils.hasText(value.toString()))) || "0".equals(value.toString())) {
	    	errors.rejectValue(field, "offer.category.required", null, null);
	    }
	}

	public void validateAddOfferAddressForm(Offer offer, Errors errors) {
		System.out.println("Walidacja address form");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.street", "offer.address.street.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "offer.address.city.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.postalCode", "offer.address.postalCode.required");

	}
}
