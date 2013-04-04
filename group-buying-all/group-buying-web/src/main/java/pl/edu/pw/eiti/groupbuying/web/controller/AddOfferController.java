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
package pl.edu.pw.eiti.groupbuying.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;
import pl.edu.pw.eiti.groupbuying.security.core.SaltedUser;

@Controller("addOfferController")
public class AddOfferController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;
	
	@Autowired
	private CityDAO cityDAO;
	
	public void submitOffer(final Offer offer) {
		SecurityContext context = SecurityContextHolder.getContext();
		SaltedUser saltedUser = (SaltedUser) context.getAuthentication().getPrincipal();
		Seller seller = new Seller();
		seller.setEmail(saltedUser.getUsername());
		offer.setSeller(seller);
		offer.setState(OfferState.WAITING);
		offerDAO.saveOffer(offer);
	}

	public List<Category> prepareCategories() {
		return Arrays.asList(Category.values());
	}
	
	public List<CityDTO> prepareCities() {
		return cityDAO.getCities();
	}

}
