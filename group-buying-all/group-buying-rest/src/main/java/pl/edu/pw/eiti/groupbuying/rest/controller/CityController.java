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

import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.model.CityConfig;
import pl.edu.pw.eiti.groupbuying.rest.service.CityService;

/**
 * Controller for city operations
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping("/cities")
public class CityController {

	private static final Logger LOG = Logger.getLogger(CityController.class);
	
	@Autowired
	private CityService cityService;

	//TODO usunac
	@Autowired
	private CityDAO cityDAO;
	
	//TODO usunac
	@Autowired
	private OfferDAO offerDAO;
	
	/**
	 * Returns a start configuration 
	 * @param latitude latitude of the current location
	 * @param longitude longitude of the current location
	 * @return
	 */
	@RequestMapping(value = "city-config", method = RequestMethod.GET)
	public @ResponseBody CityConfig getCityConfig(@RequestParam(value="latitude", required = false) final Double latitude, @RequestParam(value="longitude", required = false) final Double longitude) {
		CityConfig cityConfig = new CityConfig();
		CityDTO city;
		List<CityDTO> cities = null;
		try {
			cities = cityService.getCities();
			if(latitude != null && longitude != null) {
				city = cityService.getClosestCity(latitude, longitude);				
			} else {
				city = cityService.getDefaultCity();				
			}
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getCityConfig, latitude: " + latitude + ", longitude: " + longitude, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getCityConfig, latitude: " + latitude + ", longitude: " + longitude, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server error occured in getCityConfig, latitude: " + latitude + ", longitude: " + longitude, e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		cityConfig.setCities(cities);
		cityConfig.setMyCity(city);
		return cityConfig;
	}
	
	//TODO przeniesc do panelu admina
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public @ResponseBody CityDTO index() {
		CityDTO city = null;
		try {
			cityDAO.indexCities();
			offerDAO.indexOffers();
		} catch (DataAccessException e) {
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		return city;
	}
}
