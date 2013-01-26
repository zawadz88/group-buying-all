package pl.edu.pw.eiti.groupbuying.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import pl.edu.pw.eiti.groupbuying.security.core.CustomJdbcDaoImpl;

public class ChangePasswordJdbcDaoImpl extends CustomJdbcDaoImpl implements IChangePassword{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;
	
	//TODO zaimplementowac to?
	public void changePassword(String username, String password) {

		UserDetails user = loadUserByUsername(username);
		String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		getJdbcTemplate().update(
			"UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?",
			encodedPassword, username);
	}

}
