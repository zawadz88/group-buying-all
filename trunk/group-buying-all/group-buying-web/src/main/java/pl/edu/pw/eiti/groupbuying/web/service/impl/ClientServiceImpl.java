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
package pl.edu.pw.eiti.groupbuying.web.service.impl;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.web.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	private static final Logger LOG = Logger.getLogger(ClientServiceImpl.class);
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean saveClient(Client client) {
		encodeClient(client);
		clientDAO.saveClient(client);
		return true;
	}

	private void encodeClient(Client client) {
		Random r = new Random(System.currentTimeMillis());
		
		String salt = Double.toString(r.nextDouble() * 1000000000);
		String encodedPassword = passwordEncoder.encodePassword(client.getPassword(), salt);
		client.setPassword(encodedPassword);
		client.setSalt(salt);
		
	}

}
