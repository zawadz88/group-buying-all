package pl.edu.pw.eiti.groupbuying.rest.model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import pl.edu.pw.eiti.groupbuying.rest.util.ErrorCodeDeserializer;
import pl.edu.pw.eiti.groupbuying.rest.util.ErrorCodeSerializer;

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
	
	@JsonSerialize(using = ErrorCodeSerializer.class)
	@JsonDeserialize(using = ErrorCodeDeserializer.class)
	public static enum ErrorCode {
		UNKNOWN_ERROR(0),
		MISSING_AUTHORIZATION(401),
		APPLICATION_NOT_FOUND(402),
		MISSING_REGISTRATION_ID_PARAM(403),
		MISSING_DEVICE_NAME_PARAM(404),
		MISSING_SYSTEM_INFO_PARAM(405),
		MISSING_STATUS_PARAM(406),
		MISSING_DEVICE_TOKEN_PARAM(407),
		MISSING_ANONYMOUS_USER_ID_PARAM(408),
		MISSING_CHANNEL_URI_PARAM(409),
		INVALID_STATUS_VALUE(451),
		INVALID_DEVICE_TOKEN_VALUE(452),
		INVALID_PAYLOAD_SIZE(453),
		MISSING_SELECTED_PLATFORMS_PARAM(480),
		MISSING_MESSAGE_PARAM(481),
		DATABASE_ERROR(501),
		PUSH_ERROR(502);

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
