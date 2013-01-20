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
	
	public Map<String, String> prepareStates() {
		Map<String,String> stateMap = new LinkedHashMap<String,String>();
		stateMap.put("0", "Waiting");
		stateMap.put("1", "Active");
		stateMap.put("2", "Finished");
		return stateMap;
	}
	
	public Offer populateOffer(int offerId) {
		Offer offer = offerDAO.getOffer(offerId);
		return offer;
	}

}
