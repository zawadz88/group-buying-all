package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.rest.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDAO clientDAO;
	
	
	@Override
	public Client getClientByEmail(String email) {
		return clientDAO.getClientByEmail(email);
	}

}
