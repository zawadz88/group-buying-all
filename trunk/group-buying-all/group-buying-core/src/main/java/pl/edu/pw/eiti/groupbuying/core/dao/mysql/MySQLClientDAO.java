package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.edu.pw.eiti.groupbuying.core.dao.ClientDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;

@Repository("clientDAO")
public class MySQLClientDAO implements ClientDAO {
	
	private static final String INSERT_CLIENT = "insert into clients (email, password, salt, phone_number, first_name, last_name, street, postal_code, city) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String INSERT_AUTHORITY = "INSERT INTO client_authorities(username, authority) values (?, 'ROLE_USER')";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveClient(Client client) {
		jdbcTemplate.update(INSERT_CLIENT, new Object[] {client.getEmail(), client.getPassword(), client.getSalt(), client.getPhoneNumber(),
				client.getFirstName(), client.getLastName(), client.getAddress().getStreet(), client.getAddress().getPostalCode(), client.getAddress().getCity()});
		jdbcTemplate.update(INSERT_AUTHORITY, new Object[] {client.getEmail()});
		return true;
	}

}
