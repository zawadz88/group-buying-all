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

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer.State;

@Controller("editOfferController")
public class EditOfferController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;
	
	public void updateOffer(Offer offer) {
		System.out.println("updating offer: " + offer.toString());
		offerDAO.updateOffer(offer);
	}

	public List<Category> prepareCategories() {
		return Arrays.asList(Category.values());
	}
	
	public Map<State, String> prepareStates() {
		Map<State,String> stateMap = new LinkedHashMap<State,String>();
		stateMap.put(State.WAITING, "Waiting");
		stateMap.put(State.ACTIVE, "Active");
		stateMap.put(State.FINISHED, "Finished");
		return stateMap;
	}
	
	public Offer populateOffer(int offerId) {
		Offer offer = offerDAO.getOffer(offerId);
		System.out.println("Offer found: " + offer);
		return offer;
	}

}
