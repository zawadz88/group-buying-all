package pl.edu.pw.eiti.groupbuying.rest.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * Extension of {@link OAuth2AuthenticationEntryPoint} that adds a custom error translator and resolver
 * @author Piotr Zawadzki
 *
 */
public class ApiOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

	private HandlerExceptionResolver customHandlerExceptionResolver = new DefaultHandlerExceptionResolver();
	private WebResponseExceptionTranslator customExceptionTranslator = new DefaultWebResponseExceptionTranslator();
	private OAuth2ExceptionRenderer customHandlerExceptionRenderer = new DefaultOAuth2ExceptionRenderer();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		doCustomHandle(request, response, authException);
	}

	public void setCustomHandlerExceptionResolver(HandlerExceptionResolver customHandlerExceptionResolver) {
		this.customHandlerExceptionResolver = customHandlerExceptionResolver;
	}

	public HandlerExceptionResolver getCustomHandlerExceptionResolver() {
		return customHandlerExceptionResolver;
	}

	public WebResponseExceptionTranslator getCustomExceptionTranslator() {
		return customExceptionTranslator;
	}

	public void setCustomExceptionTranslator(WebResponseExceptionTranslator customExceptionTranslator) {
		this.customExceptionTranslator = customExceptionTranslator;
	}

	public OAuth2ExceptionRenderer getCustomHandlerExceptionRenderer() {
		return customHandlerExceptionRenderer;
	}

	public void setCustomHandlerExceptionRenderer(OAuth2ExceptionRenderer customHandlerExceptionRenderer) {
		this.customHandlerExceptionRenderer = customHandlerExceptionRenderer;
	}

	protected final void doCustomHandle(HttpServletRequest request, HttpServletResponse response, Exception authException) throws IOException, ServletException {
		try {
			ResponseEntity<OAuth2Exception> result = customExceptionTranslator.translate(authException);
			result = enhanceResponse(result, authException);
		
			HttpHeaders headers = result.getHeaders();
			CustomOAuth2Exception customException = new CustomOAuth2Exception(result.getBody(), result.getStatusCode().value());
			ResponseEntity<CustomOAuth2Exception> customResult = new ResponseEntity<CustomOAuth2Exception>(customException, headers, result.getStatusCode());
			customHandlerExceptionRenderer.handleHttpEntityResponse(customResult, new ServletWebRequest(request, response));
			response.flushBuffer();
		} catch (ServletException e) {
			// Re-use some of the default Spring dispatcher behaviour - the
			// exception came from the filter chain and
			// not from an MVC handler so it won't be caught by the dispatcher
			// (even if there is one)
			if (customHandlerExceptionResolver.resolveException(request, response, this, e) == null) {
				throw e;
			}
		} catch (IOException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// Wrap other Exceptions. These are not expected to happen
			throw new RuntimeException(e);
		}
	}
}
