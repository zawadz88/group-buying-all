package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;

public interface OfferService {

	List<OfferEssentialDTO> getOfferEssentials(Category category, int pageNumber);
	
	OfferDTO getOfferDTO(int offerId);

	List<OfferEssentialDTO> getCityOfferEssentials(String cityId, int pageNumber);
	
}
