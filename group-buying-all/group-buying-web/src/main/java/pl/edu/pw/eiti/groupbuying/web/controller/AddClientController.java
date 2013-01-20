/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Controller("addClientController")
public class AddClientController extends BaseController {

	@Autowired
	private ClientDAO clientDAO;
	
	public void submitClient(Client client) {
		System.out.println("Saving client: " + client.toString());
		clientDAO.saveClient(client);
		System.out.println("CLient saved");
	}
}
