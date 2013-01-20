package pl.edu.pw.eiti.groupbuying.web.admin.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomJdbcDaoImpl extends JdbcDaoImpl {

	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new SaltedUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities, ((SaltedUser) userFromUserQuery).getSalt());
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                String salt = rs.getString(4);
                return new SaltedUser(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, salt);
            }
        });
	}
}
