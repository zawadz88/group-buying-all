package pl.edu.pw.eiti.groupbuying.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;

@Controller
@RequestMapping("/offers")
public class OfferController {

	@Autowired
	OfferDAO offerDAO;
	
	@RequestMapping(value="{offerId}", method = RequestMethod.GET)
	public @ResponseBody Offer getOfferInJSON(@PathVariable Integer offerId) {

		Offer offer = offerDAO.getOffer(offerId);
		return offer;

	}
	
}