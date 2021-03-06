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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;

/**
 * Controller responsible for handling offer operations
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping("/offers")
public class OfferController {

	private static final Logger LOG = Logger.getLogger(OfferController.class);
	
	@Autowired
	OfferService offerService;

	/**
	 * Returns offer detail's based on its ID
	 * @param offerId
	 * @return
	 */
	@RequestMapping(value = "offer/{offerId}", method = RequestMethod.GET)
	public @ResponseBody OfferDTO getOfferById(@PathVariable("offerId") final int offerId) {
		OfferDTO offerDTO = offerService.getOfferDTO(offerId);
		return offerDTO;
	}
	
	/**
	 * Returns a list of all available offers
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public @ResponseBody List<OfferEssentialDTO> getAllOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {
		List<OfferEssentialDTO> selectedOffers = null;
		try {
			selectedOffers = offerService.getOfferEssentials(null, pageNumber);
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getAllOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getAllOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server occured in getAllOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		return selectedOffers;
	}

	/**
	 * Returns a list of available shopping offers
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "shopping", method = RequestMethod.GET)
	public @ResponseBody List<OfferEssentialDTO> getShoppingOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {
		List<OfferEssentialDTO> selectedOffers = null;
		try {
			selectedOffers = offerService.getOfferEssentials(Category.SHOPPING, pageNumber);
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getShoppingOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getShoppingOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server occured in getShoppingOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		
		return selectedOffers;
	}
	
	/**
	 * Returns a list of available travel offers
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "travel", method = RequestMethod.GET)
	public @ResponseBody List<OfferEssentialDTO> getTravelOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {
		List<OfferEssentialDTO> selectedOffers = null;
		try {
			selectedOffers = offerService.getOfferEssentials(Category.TRAVEL, pageNumber);
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getTravelOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getTravelOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server occured in getTravelOffers, page: " + pageNumber, e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		return selectedOffers;
	}

	/**
	 * Returns a list of available city offers for a given city ID
	 * @param pageNumber
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "city/{cityId}", method = RequestMethod.GET)
	public @ResponseBody List<OfferEssentialDTO> getCityOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber, @PathVariable(value = "cityId") final String cityId) {
		List<OfferEssentialDTO> selectedOffers = offerService.getCityOfferEssentials(cityId, pageNumber);
		return selectedOffers;
	}

	/**
	 * Return a list of nearby offers based on the latitude and longitude
	 * @param latitude
	 * @param longitude
	 * @param zoom
	 * @return
	 */
	@RequestMapping(value = "nearby/{latitude}/{longitude}", method = RequestMethod.GET)
	public @ResponseBody List<OfferEssentialDTO> getNearbyOffers(@PathVariable(value = "latitude") final double latitude, @PathVariable(value = "longitude") final double longitude, @RequestParam(value = "zoom", defaultValue = "1") final int zoom) {
		List<OfferEssentialDTO> selectedOffers = offerService.getClosestOffers(latitude, longitude, zoom);
		return selectedOffers;
	}
	
}
