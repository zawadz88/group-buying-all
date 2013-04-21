package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;
import pl.edu.pw.eiti.groupbuying.rest.service.ClientService;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;
import pl.edu.pw.eiti.groupbuying.rest.service.PayPalService;

@Controller
@RequestMapping("/paypal/mecl")
public class MECLController {
	
	@Resource(name = "paypalProperties")
	private Properties paypalProperties;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/checkout/{offerId}")
	public @ResponseBody String setCheckOut(HttpServletRequest request, @PathVariable int offerId, HttpServletResponse response, @RequestParam("drt") String deviceReferenceToken, Principal principal) throws IOException {
		//TODO try catche
		Offer offer = offerService.getOffer(offerId);		
		if(offer != null) {
			String token = payPalService.getExpressCheckoutToken(request, offer);
			if(token != null) {
				PaypalTransaction transaction = new PaypalTransaction();				
				Client client = clientService.getClientByEmail(principal.getName());
				if(client != null) {
					transaction.setClient(client);
					transaction.setOffer(offer);
					transaction.setTransactionToken(token);
					transaction.setState(TransactionState.STARTED);
					Date now = new Date();
					transaction.setCreated(now);
					transaction.setLastUpdated(now);
					payPalService.createTransaction(transaction);
					String redirectURL = String.format(paypalProperties.getProperty("paypal.redirectURL"), deviceReferenceToken, token);
					return redirectURL;
				}
			}
		}		
		return null;			
	}

	@RequestMapping(value = "/docheckout/{offerId}")
	public String doCheckOut(HttpServletRequest request, @PathVariable int offerId, @RequestParam("token") String token) {
		Offer offer = offerService.getOffer(offerId);
		if(offer != null) {
			String payerID = payPalService.getPayerIDFromGetExpressCheckout(token);
			boolean paymentSucceeded = payPalService.doExpressCheckout(offer, token, payerID);
			if(paymentSucceeded) {
				boolean dbUpdated = payPalService.updateTransaction(token, TransactionState.COMPLETED);
				//TODO create coupon
				if(dbUpdated) {
					return "redirect:/paypal/mecl/success";
				}				
			}
		}			
		return "redirect:/paypal/mecl/error";	
	}

	@RequestMapping(value = "/success")
	public String success() {		
		return "paypal/success";		
	}
	
	@RequestMapping(value = "/cancel")
	public String cancel(@RequestParam("token") String token) {
		payPalService.updateTransaction(token, TransactionState.CANCELLED);
		return "paypal/cancel";		
	}

	@RequestMapping(value = "/error")
	public String error() {		
		return "paypal/error";
	}
	
}
