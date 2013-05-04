package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;

public interface OfferService {

	List<OfferEssentialDTO> getOfferEssentials(Category category, int pageNumber);

	OfferDTO getOfferDTO(int offerId);
	
	Offer getOffer(int offerId);

	List<OfferEssentialDTO> getCityOfferEssentials(String cityId, int pageNumber);
	
	List<OfferEssentialDTO> getClosestOffers(double latitude, double longitude, int zoom);
		
}
