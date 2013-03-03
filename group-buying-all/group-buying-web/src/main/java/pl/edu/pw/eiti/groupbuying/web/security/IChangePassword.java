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
package pl.edu.pw.eiti.groupbuying.web.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IChangePassword extends UserDetailsService {

	/**
	 * Changes the user's password. Note that a secure implementation would require
	 * the user to supply their existing password prior to changing it.
	 * 
	 * @param username the username
	 * @param password the new password
	 */
	void changePassword(String username, String password);

}
