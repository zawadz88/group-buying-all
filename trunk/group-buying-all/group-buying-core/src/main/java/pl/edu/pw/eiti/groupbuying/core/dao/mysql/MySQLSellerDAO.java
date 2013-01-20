package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pl.edu.pw.eiti.groupbuying.core.dao.SellerDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

@Repository("sellerDAO")
public class MySQLSellerDAO implements SellerDAO {
	
	private static final String INSERT_SELLER = "insert into users (username, password, enabled, salt, email, phone_number, trade, description, street, postal_code, city) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String INSERT_GROUP_MEMBER = "INSERT INTO group_members(group_id, username) SELECT id, ? FROM groups WHERE group_name='Users'";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveSeller(Seller seller) {
		jdbcTemplate.update(INSERT_SELLER, new Object[] {seller.getUsername(), seller.getPassword(), new Integer(1), seller.getSalt(),
				seller.getEmail(), seller.getPhoneNumber(), seller.getTrade(), seller.getDescription(), seller.getAddress().getStreet(), seller.getAddress().getPostalCode(), seller.getAddress().getCity()});
		jdbcTemplate.update(INSERT_GROUP_MEMBER, new Object[] {seller.getUsername()});
		return true;
	}

}
