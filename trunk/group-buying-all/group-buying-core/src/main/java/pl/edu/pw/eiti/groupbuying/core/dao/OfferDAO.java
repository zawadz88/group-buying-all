package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;


public interface OfferDAO {
	
	public boolean saveOffer(Offer offer);

	public void updateOffer(Offer offer);
	
	public List<Category> getCategories();

	public List<Offer> getActiveOffers(String username);
	
	public List<Offer> getWaitingOffers(String username);
	
	public List<Offer> getFinishedOffers(String username);
	
	public List<Offer> getActiveOffers();
	
	public List<Offer> getWaitingOffers();
	
	public List<Offer> getFinishedOffers();
	
	public Offer getOffer(int offerId);
	
	public String getUsernameForOffer(int offerId);

}
