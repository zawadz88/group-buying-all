package pl.edu.pw.eiti.groupbuying.partner.rest.exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;


@SuppressWarnings("serial")
public class UnauthorizedException extends AbstractHttpException {

	public UnauthorizedException(String customMessage) {
		super(customMessage);
	}
	
	public UnauthorizedException(String customMessage, ErrorCode errorCode) {
		super(customMessage, errorCode);
	}
	
}