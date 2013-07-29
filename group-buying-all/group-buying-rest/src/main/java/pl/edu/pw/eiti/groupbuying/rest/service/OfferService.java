package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;

/**
 * Service for offer operations
 * @author Piotr Zawadzki
 *
 */
public interface OfferService {

	/**
	 * Returns a list of available offers for a given category and page number
	 * @param category
	 * @param pageNumber
	 * @return
	 */
	List<OfferEssentialDTO> getOfferEssentials(Category category, int pageNumber);

	/**
	 * Returns an offer DTO based on its ID
	 * @param offerId
	 * @return
	 */
	OfferDTO getOfferDTO(int offerId);
	
	/**
	 * Returns an offer based on its ID
	 * @param offerId
	 * @return
	 */	
	Offer getOffer(int offerId);

	/**
	 * Returns a list of available offers for a given city and page number 
	 * @param cityId
	 * @param pageNumber
	 * @return
	 */
	List<OfferEssentialDTO> getCityOfferEssentials(String cityId, int pageNumber);
	
	/**
	 * Returns a list of available nearby offers based on location and zoom
	 * @param latitude
	 * @param longitude
	 * @param zoom
	 * @return
	 */
	List<OfferEssentialDTO> getClosestOffers(double latitude, double longitude, int zoom);
		
}
