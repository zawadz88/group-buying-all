package pl.edu.pw.eiti.groupbuying.partner.rest.exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

/**
 * Exception thrown when the request is of HTTP type 400 Bad Request
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class BadRequestException extends AbstractHttpException {

	public BadRequestException(String customMessage) {
		super(customMessage);
	}
	
	public BadRequestException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
	
}