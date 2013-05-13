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
/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.web.admin.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.web.admin.model.PushNotification;
import pl.edu.pw.eiti.groupbuying.web.admin.service.PushService;

/**
 * 
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {
	
	private static final Logger LOG = Logger.getLogger(AdminController.class);

	@Autowired
	private OfferDAO offerDAO;

	@Autowired
	private PushService pushService;

	@RequestMapping(method = RequestMethod.GET, value = "home.do")
	public void home() {		
	}

	@RequestMapping(method = RequestMethod.GET, value = "activeOffers.do")
	public void activeOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getActiveOffers();
		model.addAttribute("offers", offers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "waitingOffers.do")
	public void watingOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getWaitingOffers();
		model.addAttribute("offers", offers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "finishedOffers.do")
	public void finishedOffers(ModelMap model) {
		List<Offer> offers = offerDAO.getFinishedOffers();
		model.addAttribute("offers", offers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "details.do")
	public void details(ModelMap model, @RequestParam(value = "offerId", required = true) int offerId) {

		Offer offer = offerDAO.getOffer(offerId);
		model.addAttribute("offer", offer);

	}

	@RequestMapping(value = "push.do", method = RequestMethod.GET)
	protected String pushGet(@ModelAttribute PushNotification pushNotification) {
		return "admin/sendPush";
	}
	
	@RequestMapping(value = "push.do", method = RequestMethod.POST)
	protected String pushPost(@ModelAttribute @Valid PushNotification pushNotification, BindingResult result) {
		if (result.hasErrors()) {
			return "admin/sendPush";
		} else {
			try {
				pushService.push(pushNotification);
			} catch (InternalServerErrorException e) {
				LOG.error("Error occured when sending a push notification.", e);
				result.reject("push.failed");
				return "admin/sendPush";
			}
			return "redirect:home.do";
		}
		
	}
}
