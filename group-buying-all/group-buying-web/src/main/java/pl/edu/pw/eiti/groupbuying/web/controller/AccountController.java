/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pw.eiti.groupbuying.web.security.IChangePassword;

//TODO zrobić jakoś porządnie
@Controller
public class AccountController extends BaseController {

	@Autowired
	private IChangePassword changePasswordDao;
	
	@RequestMapping("/account/home.do")
	public void accountHome() {		
	}
	@RequestMapping(value="/account/changePassword.do",method=RequestMethod.GET)
	public void showChangePasswordPage() {		
	}
	@RequestMapping(value="/account/changePassword.do",method=RequestMethod.POST)
	public String submitChangePasswordPage(@RequestParam("password") String newPassword) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = principal.toString();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		}
		
		changePasswordDao.changePassword(username, newPassword);
		SecurityContextHolder.clearContext();
		
		return "redirect:../home.do";
	}

}
