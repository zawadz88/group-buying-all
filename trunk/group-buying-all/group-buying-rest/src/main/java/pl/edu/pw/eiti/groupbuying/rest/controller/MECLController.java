package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.service.ClientService;
import pl.edu.pw.eiti.groupbuying.rest.service.CouponService;
import pl.edu.pw.eiti.groupbuying.rest.service.OfferService;
import pl.edu.pw.eiti.groupbuying.rest.service.PayPalService;

/**
 * Controller for handling PayPal's Mobile Express Checkout operations
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping("/paypal/mecl")
public class MECLController {

	private static final Logger LOG = Logger.getLogger(MECLController.class);
	
	@Resource(name = "paypalProperties")
	private Properties paypalProperties;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * Responsible for handling SetExpressCheckout PayPal operations
	 * @param request
	 * @param offerId
	 * @param response
	 * @param deviceReferenceToken
	 * @param principal
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkout/{offerId}")
	public @ResponseBody Callable<String> setCheckOut(final HttpServletRequest request, @PathVariable final int offerId, HttpServletResponse response, @RequestParam("drt") final String deviceReferenceToken, final Principal principal) throws IOException {
		return new Callable<String>() {
		    public String call() throws Exception {
		    	try {
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
				} catch (DataAccessException e) {
					LOG.error("DB error occured in setCheckOut, offerId: " + offerId + ", deviceReferenceToken: " + deviceReferenceToken, e);
					throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
				} catch (PersistenceException e) {
					LOG.error("DB error occured in setCheckOut, offerId: " + offerId + ", deviceReferenceToken: " + deviceReferenceToken, e);
					throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
				} catch (Exception e) {
					LOG.error("Internal server error occured in setCheckOut, offerId: " + offerId + ", deviceReferenceToken: " + deviceReferenceToken, e);
					throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
				}
				return null;
		    }
		  };
					
	}

	/**
	 * Responsible for making GetExpressCheckout and DoExpressCheckout PayPal operations
	 * @param request
	 * @param offerId
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/docheckout/{offerId}")
	public Callable<String> doCheckOut(HttpServletRequest request, @PathVariable final int offerId, @RequestParam("token") final String token) {
		return new Callable<String>() {
		    public String call() throws Exception {
		    	Offer offer = offerService.getOffer(offerId);
				if(offer != null) {
					String payerID = payPalService.getPayerIDFromGetExpressCheckout(token);
					boolean paymentSucceeded = payPalService.doExpressCheckout(offer, token, payerID);
					if(paymentSucceeded) {
						boolean dbUpdated = payPalService.updateTransaction(token, TransactionState.COMPLETED);
						Client client = payPalService.getClientFromTransactionToken(token);
						if(client != null) {
							dbUpdated = dbUpdated && couponService.createCoupon(client, offer);
						}
						if(dbUpdated) {
							return "redirect:/paypal/mecl/success";
						} else {
							LOG.error("Couldn't complete transaction, token: " + token + ", offerId: " + offerId + ", client: " + client);
						}
					} else {
						LOG.error("Error occured during doExpressCheckout, token: " + token + ", offerId: " + offerId);
					}
				}			
				return "redirect:/paypal/mecl/error";	
		    }
		  };		
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
