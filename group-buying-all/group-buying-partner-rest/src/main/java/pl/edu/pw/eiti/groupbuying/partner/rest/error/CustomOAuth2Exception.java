package pl.edu.pw.eiti.groupbuying.partner.rest.error;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom OAuth 2.0 exception that can be JSON serialized to {@link ApiError} format
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2Exception.OAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {

	private int responsecode;

	public CustomOAuth2Exception(OAuth2Exception e, int responseCode) {
		super(e.getMessage());
		this.responsecode = responseCode;
	}
	
	public int getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(int responsecode) {
		this.responsecode = responsecode;
	}

	/**
	 * A serializer that converts {@link CustomOAuth2Exception} into a custom JSON format
	 * @author Piotr Zawadzki
	 *
	 */
	public static class OAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

		public OAuth2ExceptionSerializer() {
			super(CustomOAuth2Exception.class);
		}

		@Override
		public void serialize(CustomOAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

			ErrorCode errorCode;
			if(value.getResponsecode() == HttpServletResponse.SC_UNAUTHORIZED) {
				errorCode = ErrorCode.UNAUTHORIZED;				
			} else if(value.getResponsecode() == HttpServletResponse.SC_FORBIDDEN) {
				errorCode = ErrorCode.FORBIDDEN;						
			} else {
				errorCode = ErrorCode.OAUTH2_ERROR;			
			}
			jgen.writeStartObject();
			jgen.writeNumberField("responseCode", value.getResponsecode());
			jgen.writeNumberField("errorCode", errorCode.value());
			jgen.writeStringField("errorMessage", value.getMessage());			
			jgen.writeEndObject();
		}

	}
}
