package pl.edu.pw.eiti.groupbuying.partner.rest.exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

/**
 * Base class for an exception containing a custom message and an error code
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractHttpException extends RuntimeException {
	protected String customMessage;
	protected ErrorCode errorCode;
	
	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public AbstractHttpException(String customMessage) {
		super();
		this.customMessage = customMessage;
	}

	public AbstractHttpException(String customMessage, ErrorCode errorCode) {
		super();
		this.customMessage = customMessage;
		this.errorCode = errorCode;
	}

	
}
