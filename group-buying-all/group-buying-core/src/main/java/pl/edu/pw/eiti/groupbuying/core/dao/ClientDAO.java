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
package pl.edu.pw.eiti.groupbuying.core.dao;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;

/**
 * DAO for operations on {@link Client} entities
 * @author Piotr Zawadzki
 *
 */
public interface ClientDAO {
	
	/**
	 * Persists a client
	 * @param client
	 * @return
	 */
	boolean saveClient(Client client);

	/**
	 * Gets a client entity from an email address
	 * @param email
	 * @return
	 */
	Client getClientByEmail(String email);
}
