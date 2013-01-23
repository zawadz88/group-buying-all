/**
 * 
 */
package pl.edu.pw.eiti.groupbuying.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.web.service.ClientService;

@Controller("addClientController")
public class AddClientController extends BaseController {

	@Autowired
	private ClientService clientService;
	
	public void submitClient(Client client) {
		System.out.println("Saving client: " + client.toString());
		clientService.saveClient(client);
		System.out.println("CLient saved");
	}
}
