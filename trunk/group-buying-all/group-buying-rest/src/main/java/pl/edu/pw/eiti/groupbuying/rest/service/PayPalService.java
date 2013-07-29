package pl.edu.pw.eiti.groupbuying.rest.service;

import javax.servlet.http.HttpServletRequest;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;

/**
 * Service for PayPal operations
 * @author Piotr Zawadzki
 *
 */
public interface PayPalService {

	/**
	 * Persists a given transaction
	 * @param transaction
	 */
	void createTransaction(PaypalTransaction transaction);
	
	/**
	 * Updates transaction's state
	 * @param transactionToken transaction's token 
	 * @param state new state
	 * @return
	 */
	boolean updateTransaction(String transactionToken, TransactionState state);
	
	/**
	 * Returns a client for whom a given transaction was created
	 * @param token transaction token
	 * @return
	 */
	Client getClientFromTransactionToken(String token);
	
	/**
	 * Performs a SetExpressCheckout PayPal operation and returns transaction's token if successful
	 * @param request
	 * @param offer
	 * @return
	 */
	String getExpressCheckoutToken(HttpServletRequest request, Offer offer);
	
	/**
	 * Performs a GetExpressCheckoutDetails PayPal operation
	 * @param token
	 * @return payer ID
	 */
	String getPayerIDFromGetExpressCheckout(String token);

	/**
	 * Performs a DoExpressCheckout PayPal operation
	 * @param offer
	 * @param token
	 * @param payerID
	 * @return true if successful, false otherwise
	 */
	boolean doExpressCheckout(Offer offer, String token, String payerID);
	
}
