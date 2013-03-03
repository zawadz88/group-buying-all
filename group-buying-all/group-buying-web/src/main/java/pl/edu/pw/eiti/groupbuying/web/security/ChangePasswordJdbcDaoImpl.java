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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import pl.edu.pw.eiti.groupbuying.security.core.CustomJdbcDaoImpl;

public class ChangePasswordJdbcDaoImpl extends CustomJdbcDaoImpl implements IChangePassword{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;
	
	//TODO zaimplementowac to?
	public void changePassword(String username, String password) {

		UserDetails user = loadUserByUsername(username);
		String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		getJdbcTemplate().update(
			"UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?",
			encodedPassword, username);
	}

}
