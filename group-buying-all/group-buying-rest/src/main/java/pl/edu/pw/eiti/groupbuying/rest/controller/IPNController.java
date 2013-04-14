package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.service.PayPalService;

import com.paypal.core.ConfigManager;
import com.paypal.ipn.IPNMessage;

@Controller
@RequestMapping("/paypal")
public class IPNController {

	@Resource(name = "ipnProperties")
	private Properties ipnProperties;
		
	@Autowired
	private PayPalService payPalService;
	
	@RequestMapping(value = "/ipn-endpoint")
	public @ResponseBody boolean processNotification(HttpServletRequest request) {
		IPNMessage ipnMessage = new IPNMessage(request);
		boolean isIpnVerified = ipnMessage.validate();
		if(isIpnVerified) {
			payPalService.processTransaction(ipnMessage);
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
