package pl.edu.pw.eiti.groupbuying.partner.rest.util;

import java.io.IOException;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ErrorCodeSerializer extends JsonSerializer<ErrorCode> {
	@Override
	public void serialize(final ErrorCode errorCode,
			final JsonGenerator gen, final SerializerProvider serializer)
			throws IOException, JsonProcessingException {
		gen.writeNumber(errorCode.value());
	}
}
