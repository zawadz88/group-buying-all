package pl.edu.pw.eiti.groupbuying.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller is used to provide functionality for the login page.
 * 
 * @author Mularien
 */
@Controller
public class LoginLogoutController {
	
	@RequestMapping(value="/login.do")
	public String login() {
		return "login";
	}	
}
