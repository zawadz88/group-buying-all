package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

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

@Controller
@RequestMapping("/paypal")
public class IPNController {

	@Resource(name = "ipnProperties")
	private Properties ipnProperties;
	
	private static final Logger LOG = Logger.getLogger(IPNController.class);
	
	@RequestMapping(value = "/ipn-endpoint")
	public @ResponseBody boolean processNotification(HttpServletRequest request) {
		IPNMessage ipnMessage = new IPNMessage(request);
		boolean isIpnVerified = ipnMessage.validate();
		if(isIpnVerified) {
			String transactionType = ipnMessage.getTransactionType();
			if("Adaptive Payment PAY".equals(transactionType)) {
				Map<String,String> map = ipnMessage.getIpnMap();
				if(map != null && map.containsKey("pay_key")) {
					getTransactionDetails(map.get("pay_key"));
				}
			}		 
			return true;
		} else {
			throw new BadRequestException("Notification request not valid.", ErrorCode.INVALID_NOTIFICATION);
		}
		
	}
	
	@PostConstruct
	public void initConfig() {
		ConfigManager.getInstance().load(ipnProperties);
	}
	
	private void getTransactionDetails(String payKey) {
		RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");
		PaymentDetailsRequest req = new PaymentDetailsRequest(requestEnvelope);
		req.setPayKey(payKey);
		AdaptivePaymentsService service = new AdaptivePaymentsService(ipnProperties);
		try {
			PaymentDetailsResponse resp = service.paymentDetails(req);
			if (resp != null) {
				if (resp.getResponseEnvelope().getAck().toString().equalsIgnoreCase("SUCCESS")) {
					
				} else {
				}
			}
		} catch (SSLConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
