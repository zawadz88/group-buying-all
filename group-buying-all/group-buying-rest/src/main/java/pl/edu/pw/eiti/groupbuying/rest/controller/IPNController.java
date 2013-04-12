package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;

import com.paypal.core.ConfigManager;
import com.paypal.ipn.IPNMessage;

@Controller
@RequestMapping("/paypal")
public class IPNController {

	@Resource(name = "ipnProperties")
	private Properties ipnProperties;
	
	private static final Logger LOG = Logger.getLogger(IPNController.class);
	
	@RequestMapping(value = "/ipn-endpoint", method = RequestMethod.POST)
	public @ResponseBody boolean processNotification(HttpServletRequest request) {
		IPNMessage ipnMessage = new IPNMessage(request);
		boolean isIpnVerified = ipnMessage.validate();
		if(isIpnVerified) {
			String transactionType = ipnMessage.getTransactionType();
			Map<String,String> map = ipnMessage.getIpnMap();
			String itemName = request.getParameter("item_name");
			String itemNumber = request.getParameter("item_number");
			String paymentStatus = request.getParameter("payment_status");
			String paymentAmount = request.getParameter("mc_gross");
			String paymentCurrency = request.getParameter("mc_currency");
			String txnId = request.getParameter("txn_id");
			String receiverEmail = request.getParameter("receiver_email");
			String payerEmail = request.getParameter("payer_email");
			String custom = request.getParameter("custom");
		 
			LOG.info("******* IPN (name:value) pair : " + map + "  " + "######### TransactionType : " + transactionType + "  ======== IPN verified : " + isIpnVerified);
			return true;
		} else {
			throw new BadRequestException("Notification request not valid.", ErrorCode.INVALID_NOTIFICATION);
		}
		
	}
	
	@PostConstruct
	public void initConfig() {
		ConfigManager.getInstance().load(ipnProperties);
	}
}
