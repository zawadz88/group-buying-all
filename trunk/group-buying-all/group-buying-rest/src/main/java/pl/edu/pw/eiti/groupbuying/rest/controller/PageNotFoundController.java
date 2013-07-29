/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.rest.model.ApiError;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

/**
 * This controller is used to provide functionality for the 404 page not found.
 * 
 * @author Piotr Zawadzki
 */
@Controller
public class PageNotFoundController {
	
	@RequestMapping(value="/pageNotFound")
	public @ResponseBody ApiError login(HttpServletRequest request, HttpServletResponse response) {
		ApiError error = new ApiError();
		error.setResponseCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage("Page not found");
		error.setErrorCode(ErrorCode.PAGE_NOT_FOUND);
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return error;
	}	
}
