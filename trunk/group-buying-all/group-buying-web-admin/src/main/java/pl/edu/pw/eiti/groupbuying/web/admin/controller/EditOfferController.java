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
/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.admin.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.City;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;

@Controller("editOfferController")
public class EditOfferController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;

	@Autowired
	private CityDAO cityDAO;
	
	public void updateOffer(Offer offer) {
		offerDAO.updateOffer(offer);
	}

	public List<Category> prepareCategories() {
		return Arrays.asList(Category.values());
	}

	public List<CityDTO> prepareCities() {
		return cityDAO.getCities();
	}
	
	public Map<OfferState, String> prepareStates() {
		Map<OfferState,String> stateMap = new LinkedHashMap<OfferState,String>();
		stateMap.put(OfferState.WAITING, "Waiting");
		stateMap.put(OfferState.ACTIVE, "Active");
		stateMap.put(OfferState.FINISHED, "Finished");
		return stateMap;
	}
	
	public Offer populateOffer(int offerId) {
		Offer offer = offerDAO.getOffer(offerId);
		if(offer != null && offer.getCity() == null) {
			offer.setCity(new City());
		}
		return offer;
	}

}
