package pl.edu.pw.eiti.groupbuying.rest.util;

import java.io.IOException;

import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ErrorCodeDeserializer extends JsonDeserializer<ErrorCode>{

	@Override
	public ErrorCode deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		final String jsonValue = parser.getText();
	    for (final ErrorCode enumValue : ErrorCode.values()) {
	        if (Integer.toString(enumValue.value()).equals(jsonValue)) {
	            return enumValue;
	        }
	    }
		return null;
	}

}
