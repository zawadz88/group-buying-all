package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import pl.edu.pw.eiti.groupbuying.core.dao.PaypalTransactionDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;
import pl.edu.pw.eiti.groupbuying.rest.service.PayPalService;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.paypal.core.APIService;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

@Service("payPalService")
public class PayPalServiceImpl implements PayPalService {


	private static final Logger LOG = Logger.getLogger(PayPalServiceImpl.class);
	
	private static final String OFFER_CURRENCY = "offer.currency";

	@Autowired
	private PaypalTransactionDAO paypalTransactionDAO;
	
	@Resource(name = "paypalProperties")
	private Properties paypalProperties;

	private PayPalAPIInterfaceServiceService service;

	@PostConstruct
	public void initConfig() {
		service = new PayPalAPIInterfaceServiceService(paypalProperties);
	}

	@Override
	public String getExpressCheckoutToken(HttpServletRequest request, Offer offer) {
		//disable default logging
		java.util.logging.Logger.getLogger(APIService.class.toString()).setLevel(java.util.logging.Level.OFF);

		String returnURL = paypalProperties.getProperty("groupbuying.returnURL");
		String cancelURL = paypalProperties.getProperty("groupbuying.cancelURL");

		SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
		SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();

		details.setReturnURL(String.format(returnURL, offer.getOfferId()));

		details.setCancelURL(cancelURL);

		// populate line item details
		String amountItems = Double.toString(offer.getPrice());
		String names = offer.getTitle();

		List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();

		PaymentDetailsItemType item = new PaymentDetailsItemType();
		BasicAmountType amt = new BasicAmountType();
		amt.setCurrencyID(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)));
		amt.setValue(amountItems);
		item.setQuantity(1);
		item.setName(names);
		item.setAmount(amt);
		item.setItemCategory(ItemCategoryType.PHYSICAL);
		lineItems.add(item);

		double itemTotal = Double.parseDouble(amountItems);
		double orderTotal = itemTotal;

		List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
		PaymentDetailsType paydtl = new PaymentDetailsType();
		paydtl.setPaymentAction(PaymentActionCodeType.SALE);

		BasicAmountType itemsTotal = new BasicAmountType();
		itemsTotal.setValue(Double.toString(itemTotal));
		itemsTotal.setCurrencyID(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)));
		paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)), Double.toString(orderTotal)));
		paydtl.setPaymentDetailsItem(lineItems);

		paydtl.setItemTotal(itemsTotal);
		payDetails.add(paydtl);
		details.setPaymentDetails(payDetails);

		// shipping display options
		details.setNoShipping("1");

		setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);

		SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
		expressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutReq);

		SetExpressCheckoutResponseType setExpressCheckoutResponse;
		try {
			setExpressCheckoutResponse = service.setExpressCheckout(expressCheckoutReq);
			if ("SUCCESS".equalsIgnoreCase(setExpressCheckoutResponse.getAck().toString())) {
				String token = setExpressCheckoutResponse.getToken();
				return token;
			}
		} catch (SSLConfigurationException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (InvalidCredentialException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (HttpErrorException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (InvalidResponseDataException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (ClientActionRequiredException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (MissingCredentialException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (OAuthException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (IOException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (InterruptedException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (ParserConfigurationException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		} catch (SAXException e) {
			LOG.error("getExpressCheckoutToken: " + e.getLocalizedMessage(), e);
		}
		return null;
	}

	@Override
	public String getPayerIDFromGetExpressCheckout(String token) {
		//disable default logging
		java.util.logging.Logger.getLogger(APIService.class.toString()).setLevel(java.util.logging.Level.OFF);
		GetExpressCheckoutDetailsReq req = new GetExpressCheckoutDetailsReq();
		GetExpressCheckoutDetailsRequestType reqType = new GetExpressCheckoutDetailsRequestType(token);
		req.setGetExpressCheckoutDetailsRequest(reqType);
		GetExpressCheckoutDetailsResponseType resp = null;
		try {
			resp = service.getExpressCheckoutDetails(req);
		} catch (SSLConfigurationException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InvalidCredentialException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (HttpErrorException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InvalidResponseDataException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (ClientActionRequiredException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (MissingCredentialException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (OAuthException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (IOException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InterruptedException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (ParserConfigurationException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (SAXException e) {
			LOG.error("getPayerIDFromGetExpressCheckout: " + e.getLocalizedMessage(), e);
		}
		if (resp != null) {
			if (resp.getAck().toString().equalsIgnoreCase("SUCCESS")) {
				return resp.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID();
			}
		}
		return null;
	}

	@Override
	public boolean doExpressCheckout(Offer offer, String token, String payerID) {
		//disable default logging
		java.util.logging.Logger.getLogger(APIService.class.toString()).setLevel(java.util.logging.Level.OFF);
		DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
		DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();
		details.setToken(token);
		details.setPayerID(payerID);
		details.setPaymentAction(PaymentActionCodeType.SALE);
		double itemTotalAmt = 0.00;
		double orderTotalAmt = 0.00;
		String amt = Double.toString(offer.getPrice());
		itemTotalAmt = Double.parseDouble(amt);
		orderTotalAmt += itemTotalAmt;
		PaymentDetailsType paymentDetails = new PaymentDetailsType();
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setValue(Double.toString(orderTotalAmt));
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)));
		paymentDetails.setOrderTotal(orderTotal);

		BasicAmountType itemTotal = new BasicAmountType();
		itemTotal.setValue(Double.toString(itemTotalAmt));

		itemTotal.setCurrencyID(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)));
		paymentDetails.setItemTotal(itemTotal);

		List<PaymentDetailsItemType> paymentItems = new ArrayList<PaymentDetailsItemType>();
		PaymentDetailsItemType paymentItem = new PaymentDetailsItemType();
		paymentItem.setName(offer.getTitle());
		paymentItem.setQuantity(1);
		BasicAmountType amount = new BasicAmountType();
		amount.setValue(amt);
		amount.setCurrencyID(CurrencyCodeType.fromValue(paypalProperties.getProperty(OFFER_CURRENCY)));
		paymentItem.setAmount(amount);
		paymentItems.add(paymentItem);
		paymentDetails.setPaymentDetailsItem(paymentItems);
		
		List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
		payDetailType.add(paymentDetails);
		details.setPaymentDetails(payDetailType);

		doCheckoutPaymentRequestType
				.setDoExpressCheckoutPaymentRequestDetails(details);
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
		doExpressCheckoutPaymentReq
				.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

		DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = null;
		try {
			doCheckoutPaymentResponseType = service.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
		} catch (SSLConfigurationException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InvalidCredentialException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (HttpErrorException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InvalidResponseDataException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (ClientActionRequiredException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (MissingCredentialException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (OAuthException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (IOException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (InterruptedException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (ParserConfigurationException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		} catch (SAXException e) {
			LOG.error("doExpressCheckout: " + e.getLocalizedMessage(), e);
		}
		if (doCheckoutPaymentResponseType != null) {
			if (doCheckoutPaymentResponseType.getAck().toString().equalsIgnoreCase("SUCCESS")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void createTransaction(PaypalTransaction transaction) {
		paypalTransactionDAO.createTransaction(transaction);		
	}

	@Override
	public boolean updateTransaction(String transactionToken, TransactionState state) {
		int updatedRows = paypalTransactionDAO.updateTransaction(transactionToken, state);
		if(updatedRows > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Client getClientFromTransactionToken(String token) {
		Client client = null;
		PaypalTransaction transaction = paypalTransactionDAO.getTransaction(token);
		if(transaction != null) {
			client = transaction.getClient();
		}
		return client;
	}

}
