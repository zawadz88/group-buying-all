package pl.edu.pw.eiti.groupbuying.web.admin.model;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Push notification POJO
 * @author Piotr Zawadzki
 *
 */
public class PushNotification {
	
	/**
	 * Offer's ID
	 */
	@Min(value = 1)
	private int offerId;
	
	/**
	 * Displayed message
	 */
	@NotBlank
	private String message;

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PushNotification [offerId=" + offerId + ", message=" + message + "]";
	}

}
