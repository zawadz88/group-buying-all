package pl.edu.pw.eiti.groupbuying.rest.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.edu.pw.eiti.groupbuying.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.exception.ResourceNotFoundException;
import pl.edu.pw.eiti.groupbuying.rest.exception.UnauthorizedException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

@ControllerAdvice
public class ErrorControllerAdvice {
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
    public @ResponseBody ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiError error = new ApiError();
		error.setResponseCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(ex.getCustomMessage());
		if(ex.getErrorCode() != null) {
			error.setErrorCode(ex.getErrorCode());
		} else {
			error.setErrorCode(ErrorCode.UNKNOWN_ERROR);
		}
        return error;
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
    public @ResponseBody ApiError handleBadRequestException(BadRequestException ex) {
		ApiError error = new ApiError();
		error.setResponseCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage(ex.getCustomMessage());
		if(ex.getErrorCode() != null) {
			error.setErrorCode(ex.getErrorCode());
		} else {
			error.setErrorCode(ErrorCode.UNKNOWN_ERROR);
		}
        return error;
    }
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody ApiError handleUnauthorizedException(UnauthorizedException ex) {
		ApiError error = new ApiError();
		error.setResponseCode(HttpStatus.UNAUTHORIZED.value());
		error.setErrorMessage(ex.getCustomMessage());
		if(ex.getErrorCode() != null) {
			error.setErrorCode(ex.getErrorCode());
		} else {
			error.setErrorCode(ErrorCode.UNKNOWN_ERROR);
		}
        return error;
    }

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerErrorException.class)
    public @ResponseBody ApiError handleInternalServerErrorException(InternalServerErrorException ex) {
		ApiError error = new ApiError();
		error.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMessage(ex.getCustomMessage());
		if(ex.getErrorCode() != null) {
			error.setErrorCode(ex.getErrorCode());
		} else {
			error.setErrorCode(ErrorCode.UNKNOWN_ERROR);
		}
        return error;
    }
}