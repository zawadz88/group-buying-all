package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;

@Service("offerService")
public class OfferServiceImpl implements OfferService {

	private static final int DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	private OfferDAO offerDAO;
	
	@Override
	public List<OfferEssentialDTO> getOfferEssentials(Category category, int pageNumber) {
		List<OfferEssentialDTO> selectedOffers = offerDAO.getOfferEssentialsByCategoryAndPageNumber(category, OfferState.ACTIVE, pageNumber, DEFAULT_PAGE_SIZE);
		return selectedOffers;
	}
	
	@Override
	public List<OfferEssentialDTO> getCityOfferEssentials(String cityId, int pageNumber) {
		List<OfferEssentialDTO> selectedOffers = offerDAO.getOfferEssentialsByCityAndPageNumber(cityId, OfferState.ACTIVE, pageNumber, DEFAULT_PAGE_SIZE);
		return selectedOffers;
	}

	@Override
	public OfferDTO getOfferDTO(int offerId) {
		Offer offer = getOffer(offerId);
		if(offer != null) {
			return offer.getOfferDTO();
		}		
		return null;
	}

	@Override
	public Offer getOffer(int offerId) {
		return offerDAO.getOffer(offerId);
	}

}
