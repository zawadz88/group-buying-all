package pl.edu.pw.eiti.groupbuying.rest.resolver;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import pl.edu.pw.eiti.groupbuying.rest.model.ApiError;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

public class ApiHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
		
	/**
	 * Handle the case where no request handler method was found.
	 * <p>The default implementation logs a warning, sends an HTTP 404 error, and returns
	 * an empty {@code ModelAndView}. Alternatively, a fallback view could be chosen,
	 * or the NoSuchRequestHandlingMethodException could be rethrown as-is.
	 * @param ex the NoSuchRequestHandlingMethodException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler, or {@code null} if none chosen
	 * at the time of the exception (for example, if multipart resolution failed)
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.NOT_FOUND, ErrorCode.PAGE_NOT_FOUND, HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * Handle the case where no request handler method was found for the particular HTTP request method.
	 * <p>The default implementation logs a warning, sends an HTTP 405 error, sets the "Allow" header,
	 * and returns an empty {@code ModelAndView}. Alternatively, a fallback view could be chosen,
	 * or the HttpRequestMethodNotSupportedException could be rethrown as-is.
	 * @param ex the HttpRequestMethodNotSupportedException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler, or {@code null} if none chosen
	 * at the time of the exception (for example, if multipart resolution failed)
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		String[] supportedMethods = ex.getSupportedMethods();
		if (supportedMethods != null) {
			response.setHeader("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
		}
		return getErrorModelAndView(ex, response, HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.METHOD_NOT_ALLOWED, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * Handle the case where no {@linkplain org.springframework.http.converter.HttpMessageConverter message converters}
	 * were found for the PUT or POSTed content. <p>The default implementation sends an HTTP 415 error,
	 * sets the "Accept" header, and returns an empty {@code ModelAndView}. Alternatively, a fallback
	 * view could be chosen, or the HttpMediaTypeNotSupportedException could be rethrown as-is.
	 * @param ex the HttpMediaTypeNotSupportedException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}
		return getErrorModelAndView(ex, response, HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.UNSUPPORTED_MEDIA_TYPE, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Handle the case where no {@linkplain org.springframework.http.converter.HttpMessageConverter message converters}
	 * were found that were acceptable for the client (expressed via the {@code Accept} header.
	 * <p>The default implementation sends an HTTP 406 error and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the HttpMediaTypeNotAcceptableException
	 * could be rethrown as-is.
	 * @param ex the HttpMediaTypeNotAcceptableException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.NOT_ACCEPTABLE, ErrorCode.NOT_ACCEPTABLE, HttpServletResponse.SC_NOT_ACCEPTABLE);
	}

	/**
	 * Handle the case when a required parameter is missing.
	 * <p>The default implementation sends an HTTP 400 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the MissingServletRequestParameterException
	 * could be rethrown as-is.
	 * @param ex the MissingServletRequestParameterException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.PARAMETER_MISSING, HttpServletResponse.SC_BAD_REQUEST);		
	}

	/**
	 * Handle the case when an unrecoverable binding exception occurs - e.g. required header, required cookie.
	 * <p>The default implementation sends an HTTP 400 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the exception could be rethrown as-is.
	 * @param ex the exception to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.MISSING_REQUIREMENTS, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Handle the case when a {@link org.springframework.web.bind.WebDataBinder} conversion cannot occur.
	 * <p>The default implementation sends an HTTP 500 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the TypeMismatchException could be rethrown as-is.
	 * @param ex the ConversionNotSupportedException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleConversionNotSupported(ConversionNotSupportedException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CONVERSION_NOT_SUPPORTED, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle the case when a {@link org.springframework.web.bind.WebDataBinder} conversion error occurs.
	 * <p>The default implementation sends an HTTP 400 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the TypeMismatchException could be rethrown as-is.
	 * @param ex the TypeMismatchException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleTypeMismatch(TypeMismatchException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.TYPE_MISMATCH, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Handle the case where a {@linkplain org.springframework.http.converter.HttpMessageConverter message converter}
	 * cannot read from a HTTP request.
	 * <p>The default implementation sends an HTTP 400 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the HttpMediaTypeNotSupportedException could be
	 * rethrown as-is.
	 * @param ex the HttpMessageNotReadableException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleHttpMessageNotReadable(HttpMessageNotReadableException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.MESSAGE_NOT_READABLE, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Handle the case where a {@linkplain org.springframework.http.converter.HttpMessageConverter message converter}
	 * cannot write to a HTTP request.
	 * <p>The default implementation sends an HTTP 500 error, and returns an empty {@code ModelAndView}.
	 * Alternatively, a fallback view could be chosen, or the HttpMediaTypeNotSupportedException could be
	 * rethrown as-is.
	 * @param ex the HttpMessageNotWritableException to be handled
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleHttpMessageNotWritable(HttpMessageNotWritableException ex,	HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.MESSAGE_NOT_WRITABLE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle the case where an argument annotated with {@code @Valid} such as
	 * an {@link RequestBody} or {@link RequestPart} argument fails validation.
	 * An HTTP 400 error is sent back to the client.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.INVALID_METHOD_ARGUMENT, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Handle the case where an {@linkplain RequestPart @RequestPart}, a {@link MultipartFile},
	 * or a {@code javax.servlet.http.Part} argument is required but is missing.
	 * An HTTP 400 error is sent back to the client.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleMissingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.MISSING_SERVLET_REQUEST_PART, HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * Handle the case where an {@linkplain ModelAttribute @ModelAttribute} method
	 * argument has binding or validation errors and is not followed by another
	 * method argument of type {@link BindingResult}.
	 * By default an HTTP 400 error is sent back to the client.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler
	 * @return an empty ModelAndView indicating the exception was handled
	 * @throws IOException potentially thrown from response.sendError()
	 */
	protected ModelAndView handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		return getErrorModelAndView(ex, response, HttpStatus.BAD_REQUEST, ErrorCode.INVALID_BINDING, HttpServletResponse.SC_BAD_REQUEST);
	}
	
	private ModelAndView getErrorModelAndView(Exception ex, HttpServletResponse response, HttpStatus httpStatus, ErrorCode errorCode, int statusCode) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new MappingJacksonJsonView());
		ApiError error = new ApiError();
		error.setResponseCode(httpStatus.value());
		error.setErrorMessage(ex.getMessage());
		error.setErrorCode(errorCode);
		mav.addAllObjects(error.getErrorAsMap());
		response.setStatus(statusCode);
		return mav;
	}
}
