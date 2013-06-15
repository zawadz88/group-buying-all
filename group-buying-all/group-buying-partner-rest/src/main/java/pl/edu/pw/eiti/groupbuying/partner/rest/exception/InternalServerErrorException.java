package pl.edu.pw.eiti.groupbuying.partner.rest.exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

@SuppressWarnings("serial")
public class InternalServerErrorException extends AbstractHttpException {

	public InternalServerErrorException(String customMessage) {
		super(customMessage);
	}
	
	public InternalServerErrorException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
	
}