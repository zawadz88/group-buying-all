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
package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;


public interface OfferDAO {
	
	boolean saveOffer(Offer offer);

	void updateOffer(Offer offer);
		
	List<OfferEssentialDTO> getOfferEssentialsByCategoryAndPageNumber(Category category, OfferState state, int pageNumber, int pageSize);

	List<Offer> getActiveOffers(String username);
	
	List<Offer> getWaitingOffers(String username);
	
	List<Offer> getFinishedOffers(String username);
	
	List<Offer> getActiveOffers();
	
	List<Offer> getWaitingOffers();
	
	List<Offer> getFinishedOffers();
	
	Offer getOffer(int offerId);
	
	String getUsernameForOffer(int offerId);

	List<OfferEssentialDTO> getOfferEssentialsByCityAndPageNumber(String cityId, OfferState state, int pageNumber, int pageSize);

	int indexOffers();

	List<OfferEssentialDTO> getClosestOffers(double latitude, double longitude, double searchRadius);

}
