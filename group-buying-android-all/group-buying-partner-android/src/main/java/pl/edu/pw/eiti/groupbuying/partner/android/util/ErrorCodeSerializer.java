package pl.edu.pw.eiti.groupbuying.partner.android.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ApiError.ErrorCode;

public class ErrorCodeSerializer extends JsonSerializer<ErrorCode> {
	@Override
	public void serialize(final ErrorCode errorCode,
			final JsonGenerator gen, final SerializerProvider serializer)
			throws IOException, JsonProcessingException {
		gen.writeNumber(errorCode.value());
	}
}
