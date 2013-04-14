package pl.edu.pw.eiti.groupbuying.rest.service;

import com.paypal.ipn.IPNMessage;

public interface PayPalService {

	void processTransaction(IPNMessage ipnMessage);	
	
}
