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
	
	public boolean saveOffer(Offer offer);

	public void updateOffer(Offer offer);
		
	public List<OfferEssentialDTO> getOfferEssentialsByCategoryAndPageNumber(Category category, OfferState state, int pageNumber, int pageSize);

	public List<Offer> getActiveOffers(String username);
	
	public List<Offer> getWaitingOffers(String username);
	
	public List<Offer> getFinishedOffers(String username);
	
	public List<Offer> getActiveOffers();
	
	public List<Offer> getWaitingOffers();
	
	public List<Offer> getFinishedOffers();
	
	public Offer getOffer(int offerId);
	
	public String getUsernameForOffer(int offerId);

}
