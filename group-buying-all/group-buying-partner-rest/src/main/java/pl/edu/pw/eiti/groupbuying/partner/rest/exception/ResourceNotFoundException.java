package pl.edu.pw.eiti.groupbuying.partner.rest.exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

/**
 * Exception thrown when the request is of HTTP type 404 Page Not found
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends AbstractHttpException {

	public ResourceNotFoundException(String customMessage) {
		super(customMessage);
	}
	
	public ResourceNotFoundException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
}