package pl.edu.pw.eiti.groupbuying.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is used to provide functionality for the login page.
 * 
 * @author Mularien
 */
@Controller
public class LoginLogoutController extends BaseController{
	@RequestMapping(/*method=RequestMethod.GET,*/value="/login.do")
	public String login() {
		return "login";
	}	
}
