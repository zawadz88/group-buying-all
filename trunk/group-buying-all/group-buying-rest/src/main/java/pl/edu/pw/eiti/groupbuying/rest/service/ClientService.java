package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;

public interface ClientService {
	
	Client getClientByEmail(String email);
	
}
