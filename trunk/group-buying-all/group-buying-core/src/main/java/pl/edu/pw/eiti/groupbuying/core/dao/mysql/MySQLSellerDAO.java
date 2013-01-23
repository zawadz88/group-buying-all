package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.edu.pw.eiti.groupbuying.core.dao.SellerDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

@Repository("sellerDAO")
public class MySQLSellerDAO implements SellerDAO {
	
	private static final String INSERT_SELLER = "insert into sellers (email, name, password, enabled, salt, nip, phone_number, trade, description, street, postal_code, city) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String INSERT_AUTHORITY = "INSERT INTO seller_authorities(username, authority) values (?, 'ROLE_USER')";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveSeller(Seller seller) {
		jdbcTemplate.update(INSERT_SELLER, new Object[] {seller.getEmail(), seller.getName(), seller.getPassword(), new Integer(1), seller.getSalt(), seller.getNip(),
				 seller.getPhoneNumber(), seller.getTrade(), seller.getDescription(), seller.getAddress().getStreet(), seller.getAddress().getPostalCode(), seller.getAddress().getCity()});
		jdbcTemplate.update(INSERT_AUTHORITY, new Object[] {seller.getEmail()});
		return true;
	}

}
