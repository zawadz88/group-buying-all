package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;

/**
 * Service for client operations
 * @author Piotr Zawadzki
 *
 */
public interface ClientService {
	
	/**
	 * Returns a client based on his email
	 * @param email
	 * @return
	 */
	Client getClientByEmail(String email);
	
}
