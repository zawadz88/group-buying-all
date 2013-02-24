/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer.State;
import pl.edu.pw.eiti.groupbuying.security.core.SaltedUser;

@Controller("addOfferController")
public class AddOfferController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;
	
	public void submitOffer(final Offer offer) {
		System.out.println("Saving offer: " + offer.toString());
		SecurityContext context = SecurityContextHolder.getContext();
		SaltedUser saltedUser = (SaltedUser) context.getAuthentication().getPrincipal();
		offer.setUsername(saltedUser.getUsername());
		offer.setState(State.WAITING);
		offerDAO.saveOffer(offer);
	}
	
	public List<Category> prepareCategories() {
		return offerDAO.getCategories();
	}

}
