package pl.edu.pw.eiti.groupbuying.web.admin.model;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class PushNotification {
	
	@Min(value = 1)
	private int offerId;
	
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
