package pl.edu.pw.eiti.groupbuying.rest.exception;

import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;



@SuppressWarnings("serial")
public class BadRequestException extends AbstractHttpException {

	public BadRequestException(String customMessage) {
		super(customMessage);
	}
	
	public BadRequestException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
	
}