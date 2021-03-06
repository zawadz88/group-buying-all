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
package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Repository("clientDAO")
public class MySQLClientDAO implements ClientDAO {
	
	private static final String INSERT_AUTHORITY = "INSERT INTO client_authorities(username, authority) values (?, 'ROLE_USER')";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean saveClient(Client client) {
		entityManager.persist(client);
		entityManager.flush();
		jdbcTemplate.update(INSERT_AUTHORITY, new Object[] {client.getEmail()});
		return true;
	}

	@Override
	public Client getClientByEmail(String email) {
		Client client = entityManager.find(Client.class, email);
		return client;
	}

}
