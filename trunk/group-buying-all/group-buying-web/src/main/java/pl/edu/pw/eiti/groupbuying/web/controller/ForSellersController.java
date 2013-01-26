/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.security.core.SaltedUser;

/**
 * 
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping(value = "/sellers")
public class ForSellersController extends BaseController {

	@Autowired
	private OfferDAO offerDAO;
	
	@RequestMapping(method = RequestMethod.GET, value = "forSellers.do")
	public void home() {		
	}

	@RequestMapping(method = RequestMethod.GET, value = "activeOffers.do")
	public void activeOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getActiveOffers(getUsername());
		model.addAttribute("offers", offers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "waitingOffers.do")
	public void watingOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getWaitingOffers(getUsername());
		model.addAttribute("offers", offers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "finishedOffers.do")
	public void finishedOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getFinishedOffers(getUsername());
		model.addAttribute("offers", offers);
	}

	private String getUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		SaltedUser saltedUser = (SaltedUser) context.getAuthentication().getPrincipal();
		String username = saltedUser.getUsername();
		return username;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "details.do")
	public void details(ModelMap model, @RequestParam(value = "offerId", required = true) int offerId) {
		String username = getUsername();
		if (username.equals(offerDAO.getUsernameForOffer(offerId))) {
			Offer offer = offerDAO.getOffer(offerId);
			model.addAttribute("offer", offer);
		}
	}
	
}
