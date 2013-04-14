package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
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

	
	@RequestMapping(value = "/checkout/{offerId}")
	public String setCheckOut(HttpServletRequest request, @PathVariable int offerId, HttpServletResponse response, @RequestParam("drt") String deviceReferenceToken) throws IOException {
		OfferDTO offer = offerService.getOfferDTO(offerId);
		
		if(offer != null) {
			String token = payPalService.getExpressCheckoutToken(request, offer);
			if(token != null) {
				response.sendRedirect(String.format(paypalProperties.getProperty("paypal.redirectURL"), deviceReferenceToken, token));
				return null;
			}
		}		
		return "redirect:/paypal/mecl/error";			
	}

	@RequestMapping(value = "/docheckout/{offerId}")
	public String doCheckOut(HttpServletRequest request, @PathVariable int offerId, @RequestParam("token") String token) {
		OfferDTO offer = offerService.getOfferDTO(offerId);
		if(offer != null) {
			String payerID = payPalService.getPayerIDFromGetExpressCheckout(token);
			boolean paymentSucceeded = payPalService.doExpressCheckout(offer, token, payerID);
			if(paymentSucceeded) {
				//TODO save in DB
				System.out.println("yupi!");
				return "redirect:/paypal/mecl/success";				
			}
		}			
		return "redirect:/paypal/mecl/error";	
	}

	@RequestMapping(value = "/success")
	public String success() {		
		return "paypal/success";		
	}
	@RequestMapping(value = "/cancel")
	public String cancel() {		
		return "paypal/cancel";		
	}

	@RequestMapping(value = "/error")
	public String error() {
		
		return "paypal/error";
	}
	
}
