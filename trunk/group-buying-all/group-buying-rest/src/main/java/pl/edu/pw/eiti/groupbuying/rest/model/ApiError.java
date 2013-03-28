package pl.edu.pw.eiti.groupbuying.rest.model;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pw.eiti.groupbuying.rest.util.ErrorCodeDeserializer;
import pl.edu.pw.eiti.groupbuying.rest.util.ErrorCodeSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ApiError {
	private int responseCode;
	private ErrorCode errorCode;
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
	
	@JsonSerialize(using = ErrorCodeSerializer.class)
	@JsonDeserialize(using = ErrorCodeDeserializer.class)
	public static enum ErrorCode {
		PARAMETER_MISSING(4001),
		MISSING_REQUIREMENTS(4002),
		TYPE_MISMATCH(4003),
		MESSAGE_NOT_READABLE(4004),
		INVALID_METHOD_ARGUMENT(4005),
		MISSING_SERVLET_REQUEST_PART(4006),
		INVALID_BINDING(4007),
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
