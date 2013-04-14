package pl.edu.pw.eiti.groupbuying.rest.service;

import javax.servlet.http.HttpServletRequest;

import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;


public interface PayPalService {

	String getExpressCheckoutToken(HttpServletRequest request, OfferDTO offer);
	
	String getPayerIDFromGetExpressCheckout(String token);

	boolean doExpressCheckout(OfferDTO offer, String token, String payerID);
	
}
