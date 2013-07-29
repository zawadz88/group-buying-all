package pl.edu.pw.eiti.groupbuying.partner.rest.model;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pw.eiti.groupbuying.partner.rest.util.ErrorCodeDeserializer;
import pl.edu.pw.eiti.groupbuying.partner.rest.util.ErrorCodeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * POJO containing information about an API error that occurred
 * @author Piotr Zawadzki
 *
 */
public class ApiError {
	
	/**
	 * HTTP response code
	 */
	private int responseCode;
	
	/**
	 * API specific error code
	 */
	private ErrorCode errorCode;
	
	/**
	 * Description of the error
	 */
	private String errorMessage;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "Error [responseCode=" + responseCode + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
	
	@JsonIgnore
	public Map<String, Object> getErrorAsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorMessage", errorMessage);
		map.put("errorCode", errorCode);
		map.put("responseCode", responseCode);
		return map;
	}
	
	/**
	 * Enumeration containing API specific error codes
	 * @author Piotr Zawadzki
	 *
	 */
	@JsonSerialize(using = ErrorCodeSerializer.class)
	@JsonDeserialize(using = ErrorCodeDeserializer.class)
	public static enum ErrorCode {
		PARAMETER_MISSING(40001),
		MISSING_REQUIREMENTS(40002),
		TYPE_MISMATCH(40003),
		MESSAGE_NOT_READABLE(40004),
		INVALID_METHOD_ARGUMENT(40005),
		MISSING_SERVLET_REQUEST_PART(40006),
		INVALID_BINDING(40007),
		COUPON_NOT_FOUND(40012),
		INVALID_SECURITY_KEY(40013),
		INVALID_PARTNER(40014),
		UNAUTHORIZED(401),
		OAUTH2_ERROR(4010),
		FORBIDDEN(403),
		PAGE_NOT_FOUND(404),
		METHOD_NOT_ALLOWED(405),
		NOT_ACCEPTABLE(406),
		UNSUPPORTED_MEDIA_TYPE(415),
		UNKNOWN_ERROR(5000),
		DATABASE_ERROR(5001),
		CONVERSION_NOT_SUPPORTED(5002),
		MESSAGE_NOT_WRITABLE(5003);

		private final int value;

		private ErrorCode(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		@Override
		public String toString() {
			return Integer.toString(value);
		}

		public static ErrorCode valueOf(int value) {
			for (ErrorCode status : values()) {
				if (status.value == value) {
					return status;
				}
			}
			throw new IllegalArgumentException("No matching constant for ["
					+ value + "]");
		}

	}

}
