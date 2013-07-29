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
package pl.edu.pw.eiti.groupbuying.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.web.service.ClientService;

/**
 * Controller for adding new clients
 * @author Piotr Zawadzki
 *
 */
@Controller("addClientController")
public class AddClientController extends BaseController {

	@Autowired
	private ClientService clientService;
	
	/**
	 * Adds a new client
	 * @param client
	 */
	public void submitClient(Client client) {
		clientService.saveClient(client);
	}
}
