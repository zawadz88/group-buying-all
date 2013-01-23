/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.admin.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer.State;

@Controller("editOfferController")
public class EditOfferController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;
	
	public void updateOffer(Offer offer) {
		System.out.println("updating offer: " + offer.toString());
		offerDAO.updateOffer(offer);
	}

	public List<Category> prepareCategories() {
		return offerDAO.getCategories();
	}
	
	public Map<State, String> prepareStates() {
		Map<State,String> stateMap = new LinkedHashMap<State,String>();
		stateMap.put(State.WAITING, "Waiting");
		stateMap.put(State.ACTIVE, "Active");
		stateMap.put(State.FINISHED, "Finished");
		return stateMap;
	}
	
	public Offer populateOffer(int offerId) {
		Offer offer = offerDAO.getOffer(offerId);
		return offer;
	}

}
