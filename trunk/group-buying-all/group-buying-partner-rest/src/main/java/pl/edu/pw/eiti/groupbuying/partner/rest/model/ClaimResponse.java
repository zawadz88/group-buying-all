package pl.edu.pw.eiti.groupbuying.partner.rest.model;

/**
 * Enumaration describing possible response codes for claiming a coupon
 * @author Piotr Zawadzki
 *
 */
public enum ClaimResponse {
	CLAIMED(0), EXPIRED(1), ALREADY_USED(2);
	
	private int code;

	private ClaimResponse(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static ClaimResponse getClaimResponse(final int value) {
		ClaimResponse response = null;
		switch (value) {
		case 0:
			response = CLAIMED;
			break;
		case 1:
			response = EXPIRED;
			break;
		case 2:
			response = ALREADY_USED;
			break;
		default:
			throw new IllegalArgumentException("value: '" + value
					+ "' not permitted for ClaimResponse!");
		}
		return response;
	}
}
