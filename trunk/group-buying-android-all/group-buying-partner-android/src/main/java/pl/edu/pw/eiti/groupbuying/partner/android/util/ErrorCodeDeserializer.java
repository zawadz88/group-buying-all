package pl.edu.pw.eiti.groupbuying.partner.android.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ApiError.ErrorCode;

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
