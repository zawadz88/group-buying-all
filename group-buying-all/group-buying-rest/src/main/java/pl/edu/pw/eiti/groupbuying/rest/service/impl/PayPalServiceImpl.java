package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.rest.service.PayPalService;

import com.paypal.core.ConfigManager;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.ipn.IPNMessage;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PaymentDetailsRequest;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

@Service("payPalService")
public class PayPalServiceImpl implements PayPalService {

	private static final String ADAPTIVE_PAYMENT_PAY_TRANSACTION_TYPE = "Adaptive Payment PAY";

	private static final String PAY_KEY = "pay_key";
	private static final String TRANSACTION_ID_KEY = "transaction[0].id";

	private static final Logger LOG = Logger.getLogger(PayPalServiceImpl.class);
	
	@Resource(name = "ipnProperties")
	private Properties ipnProperties;
	
	@Override
	public void processTransaction(IPNMessage ipnMessage) {
		String transactionType = ipnMessage.getTransactionType();
		if(ADAPTIVE_PAYMENT_PAY_TRANSACTION_TYPE.equals(transactionType)) {
			Map<String,String> map = ipnMessage.getIpnMap();
			if(map != null && map.containsKey(TRANSACTION_ID_KEY)) {
				PaymentDetailsResponse transactionDetails = getTransactionDetails(map.get(TRANSACTION_ID_KEY));
				boolean transactionValid = validateTransaction(transactionDetails);
				if(transactionValid) {
					
				} else {
					
				}
			}
		} else {
			LOG.warn("Transaction type not suppported: " + transactionType);
		}
	}

	private PaymentDetailsResponse getTransactionDetails(String transactionId) {
		PaymentDetailsResponse resp = null;
		RequestEnvelope requestEnvelope = new RequestEnvelope("en_GB");
		PaymentDetailsRequest req = new PaymentDetailsRequest(requestEnvelope);
		//req.setPayKey(payKey);
		req.setTransactionId(transactionId);
		AdaptivePaymentsService service = new AdaptivePaymentsService(ipnProperties);
		try {
			resp = service.paymentDetails(req);			
		} catch (SSLConfigurationException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (InvalidCredentialException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (HttpErrorException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (InvalidResponseDataException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (ClientActionRequiredException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (MissingCredentialException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (OAuthException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (InterruptedException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		} catch (IOException e) {
			LOG.error("getTransactionDetails: " + e.getLocalizedMessage(), e);
		}
		return resp;
	}
	
	private boolean validateTransaction(PaymentDetailsResponse transactionDetails) {
		if (transactionDetails != null) {
			if (transactionDetails.getResponseEnvelope().getAck().toString().equalsIgnoreCase("SUCCESS")) {
				String sellerEmail = ConfigManager.getInstance().getValue("acctc1.email");
				String currency = ConfigManager.getInstance().getValue("offer.currency");
			}
		}
		return false;
	}
}
