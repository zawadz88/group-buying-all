package pl.edu.pw.eiti.groupbuying.rest.exception;

import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends AbstractHttpException {

	public ResourceNotFoundException(String customMessage) {
		super(customMessage);
	}
	
	public ResourceNotFoundException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
}