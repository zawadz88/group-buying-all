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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;

@Controller
@RequestMapping("/offers")
public class OfferController {

	@Autowired
	OfferService offerService;

	@RequestMapping(value = "all", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getAllOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {

		List<Offer> selectedOffers = offerService.getOffers(null, pageNumber);
		return selectedOffers;
	}

	@RequestMapping(value = "shopping", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getShoppingOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {

		List<Offer> selectedOffers = offerService.getOffers(Category.SHOPPING, pageNumber);
		return selectedOffers;
	}
	
	@RequestMapping(value = "travel", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getTravelOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber) {

		List<Offer> selectedOffers = offerService.getOffers(Category.TRAVEL, pageNumber);
		return selectedOffers;
	}

	@RequestMapping(value = "city", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getCityOffers(@RequestParam(value = "page", defaultValue = "0") final int pageNumber, @RequestParam(value = "cityId") final int cityId) {
		List<Offer> selectedOffers = offerService.getOffers(Category.CITY, pageNumber);
		//TODO po id miasta
		return selectedOffers;
	}

	@RequestMapping(value = "nearby", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getNearbyOffers(@RequestParam(value = "latitude") final double latitude, @RequestParam(value = "longitude") final double longitude) {
		List<Offer> selectedOffers = offerService.getOffers(Category.CITY, 0);
		//TODO przeszukac przestrzennie
		return selectedOffers;
	}
	
}
