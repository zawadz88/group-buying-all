package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer.State;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;

@Service("offerService")
public class OfferServiceImpl implements OfferService {

	private static final int DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	private OfferDAO offerDAO;
	
	@Override
	public List<Offer> getOffers(Category category, int pageNumber) {
		List<Offer> selectedOffers = offerDAO.getOffersByCategoryAndPageNumber(category, State.ACTIVE, pageNumber, DEFAULT_PAGE_SIZE);
		return selectedOffers;
	}

}
