package pl.edu.pw.eiti.groupbuying.android.gcm.model;

import java.io.Serializable;

public class PushNotification implements Serializable {
	private int offerId;
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
