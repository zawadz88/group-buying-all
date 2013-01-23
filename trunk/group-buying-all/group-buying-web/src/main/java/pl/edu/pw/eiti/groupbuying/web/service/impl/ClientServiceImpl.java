package pl.edu.pw.eiti.groupbuying.web.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;
import pl.edu.pw.eiti.groupbuying.web.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean saveClient(Client client) {
		System.out.println("Saving client: " + client.toString());
		encodeClient(client);
		clientDAO.saveClient(client);
		System.out.println("Client saved");
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
