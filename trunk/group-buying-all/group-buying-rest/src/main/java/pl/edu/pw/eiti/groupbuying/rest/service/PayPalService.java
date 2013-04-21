package pl.edu.pw.eiti.groupbuying.rest.service;

import javax.servlet.http.HttpServletRequest;

import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;


public interface PayPalService {

	void createTransaction(PaypalTransaction transaction);
	
	boolean updateTransaction(String transactionToken, TransactionState state);
	
	String getExpressCheckoutToken(HttpServletRequest request, Offer offer);
	
	String getPayerIDFromGetExpressCheckout(String token);

	boolean doExpressCheckout(Offer offer, String token, String payerID);
	
}
