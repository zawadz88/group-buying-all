package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.SellerDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Seller;

@Repository("sellerDAO")
public class MySQLSellerDAO implements SellerDAO {
	
	private static final String INSERT_AUTHORITY = "INSERT INTO seller_authorities(username, authority) values (?, 'ROLE_USER')";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public boolean saveSeller(final Seller seller) {
		entityManager.persist(seller);
		entityManager.flush();
		
		jdbcTemplate.update(INSERT_AUTHORITY, new Object[] {seller.getEmail()});
		return true;
	}

}
