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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.rest.service.CityService;

@Controller
@RequestMapping("/cities")
public class CityController {

	private static final Logger LOG = Logger.getLogger(CityController.class);
	
	@Autowired
	CityService cityService;

	@RequestMapping(value = "city", method = RequestMethod.GET)
	public @ResponseBody CityDTO getCity(@RequestParam(value="latitude") final double latitude, @RequestParam(value="longitude") final double longitude) {
		CityDTO city = cityService.getClosetCity(latitude, longitude);
		return city;
	}
	
}
