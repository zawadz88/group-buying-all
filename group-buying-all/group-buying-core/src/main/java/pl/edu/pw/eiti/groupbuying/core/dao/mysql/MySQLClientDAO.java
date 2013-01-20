package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Repository("clientDAO")
public class MySQLClientDAO implements ClientDAO {
	
	private static final String INSERT_CLIENT = "insert into clients (username, password, email, phone_number, first_name, last_name, street, postal_code, city) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveClient(Client client) {
		jdbcTemplate.update(INSERT_CLIENT, new Object[] {client.getUsername(), client.getPassword(), client.getEmail(), client.getPhoneNumber(),
				client.getFirstName(), client.getLastName(), client.getAddress().getStreet(), client.getAddress().getPostalCode(), client.getAddress().getCity()});
		return true;
	}

}
