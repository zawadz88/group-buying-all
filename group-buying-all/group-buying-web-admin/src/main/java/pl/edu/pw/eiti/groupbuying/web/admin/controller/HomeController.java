/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is used to provide functionality for the home page.
 * 
 * @author Mularien
 *
 */
@Controller
public class HomeController extends BaseController {

	@RequestMapping(method=RequestMethod.GET,value="/home.do")
	public void home() {
		
	}
}
