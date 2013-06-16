package pl.edu.pw.eiti.groupbuying.partner.android.api.impl;

import java.io.IOException;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ApiError;

public class ApiErrorException extends IOException {

	ApiError apiError;

	public ApiError getApiError() {
		return apiError;
	}

	public ApiErrorException(ApiError apiError) {
		this.apiError = apiError;
	}
	
}
