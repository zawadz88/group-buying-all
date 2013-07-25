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

/**
 * DAO for operations on offers
 * @author Piotr Zawadzki
 *
 */
public interface OfferDAO {
	
	/**
	 * Persists an offer
	 * @param offer
	 * @return
	 */
	boolean saveOffer(Offer offer);

	/**
	 * Updates offer info
	 * @param offer
	 */
	void updateOffer(Offer offer);
	
	/**
	 * Gets a list of {@link OfferEssentialDTO} objects based on the category, state, page number and page size
	 * @param category category of offers
	 * @param state offer state
	 * @param pageNumber number of the page to return
	 * @param pageSize number of entries on the page
	 * @return
	 */
	List<OfferEssentialDTO> getOfferEssentialsByCategoryAndPageNumber(Category category, OfferState state, int pageNumber, int pageSize);

	/**
	 * Gets a list of active offers published by a given seller
	 * @param username
	 * @return
	 */
	List<Offer> getActiveOffers(String username);
	
	/**
	 * Gets a list of waiting offers published by a given seller
	 * @param username
	 * @return
	 */
	List<Offer> getWaitingOffers(String username);
	
	/**
	 * Gets a list of finished offers published by a given seller
	 * @param username
	 * @return
	 */
	List<Offer> getFinishedOffers(String username);
	
	/**
	 * Gets a list of all currently active offers
	 * @return
	 */
	List<Offer> getActiveOffers();
	
	/**
	 * Gets a list of all currently waiting offers
	 * @return
	 */
	List<Offer> getWaitingOffers();
	
	/**
	 * Gets a list of all currently finished offers
	 * @return
	 */
	List<Offer> getFinishedOffers();
	
	/**
	 * Gets an offer based on its ID
	 * @param offerId
	 * @return
	 */
	Offer getOffer(int offerId);
	
	/**
	 * Return an email of the seller who published an offer with a given ID
	 * @param offerId
	 * @return
	 */
	String getUsernameForOffer(int offerId);

	/**
	 * Gets a list of {@link OfferEssentialDTO} objects based on the city ID, state, page number and page size
	 * @param cityId city ID 
	 * @param state offer state
	 * @param pageNumber number of the page to return
	 * @param pageSize number of entries on the page
	 * @return
	 */
	List<OfferEssentialDTO> getOfferEssentialsByCityAndPageNumber(String cityId, OfferState state, int pageNumber, int pageSize);

	/**
	 * Indexes offers for the search engine
	 * @return
	 */
	int indexOffers();

	/**
	 * Gets a list of {@link OfferEssentialDTO} objects that are close to the specified location
	 * @param latitude latitude of the place
	 * @param longitude longitude of the place
	 * @param searchRadius a radius of the search
	 * @return
	 */
	List<OfferEssentialDTO> getClosestOffers(double latitude, double longitude, double searchRadius);
	
	/**
	 * Increments offer's sold counter by 1
	 * @param offerId
	 */
	void incrementSoldCount(int offerId);

}
