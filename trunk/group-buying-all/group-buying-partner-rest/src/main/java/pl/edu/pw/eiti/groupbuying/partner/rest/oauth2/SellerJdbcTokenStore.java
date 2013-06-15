package pl.edu.pw.eiti.groupbuying.partner.rest.oauth2;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;


/**
 * Implementation of token services that stores tokens in a database. Overrides default SQL statements witha  different table name
 * 
 * @author Ken Dombeck
 * @author Luke Taylor
 * @author Dave Syer
 * @author Piotr Zawadzki
 */
public class SellerJdbcTokenStore implements TokenStore {
	
		private static final Log LOG = LogFactory.getLog(SellerJdbcTokenStore.class);

		private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into seller_oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

		private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select token_id, token from seller_oauth_access_token where token_id = ?";

		private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from seller_oauth_access_token where token_id = ?";

		private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select token_id, token from seller_oauth_access_token where authentication_id = ?";

		private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = "select token_id, token from seller_oauth_access_token where user_name = ?";

		private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = "select token_id, token from seller_oauth_access_token where client_id = ?";

		private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = "delete from seller_oauth_access_token where token_id = ?";

		private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = "delete from seller_oauth_access_token where refresh_token = ?";

		private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into seller_oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";

		private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select token_id, token from seller_oauth_refresh_token where token_id = ?";

		private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from seller_oauth_refresh_token where token_id = ?";

		private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from seller_oauth_refresh_token where token_id = ?";

		private String insertAccessTokenSql = DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT;

		private String selectAccessTokenSql = DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT;

		private String selectAccessTokenAuthenticationSql = DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

		private String selectAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT;

		private String selectAccessTokensFromUserNameSql = DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT;

		private String selectAccessTokensFromClientIdSql = DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT;

		private String deleteAccessTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT;

		private String insertRefreshTokenSql = DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT;

		private String selectRefreshTokenSql = DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT;

		private String selectRefreshTokenAuthenticationSql = DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

		private String deleteRefreshTokenSql = DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT;

		private String deleteAccessTokenFromRefreshTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT;

		private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

		private final JdbcTemplate jdbcTemplate;

		public SellerJdbcTokenStore(DataSource dataSource) {
			Assert.notNull(dataSource, "DataSource required");
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}

		public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
			this.authenticationKeyGenerator = authenticationKeyGenerator;
		}

		public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
			OAuth2AccessToken accessToken = null;

			String key = authenticationKeyGenerator.extractKey(authentication);
			try {
				accessToken = jdbcTemplate.queryForObject(selectAccessTokenFromAuthenticationSql,
						new RowMapper<OAuth2AccessToken>() {
							public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
								return deserializeAccessToken(rs.getBytes(2));
							}
						}, key);
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.debug("Failed to find access token for authentication " + authentication);
				}
			}
			catch (IllegalArgumentException e) {
				LOG.error("Could not extract access token for authentication " + authentication);
			}

			if (accessToken != null
					&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
				removeAccessToken(accessToken.getValue());
				// Keep the store consistent (maybe the same user is represented by this authentication but the details have
				// changed)
				storeAccessToken(accessToken, authentication);
			}
			return accessToken;
		}

		public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
			String refreshToken = null;
			if (token.getRefreshToken() != null) {
				refreshToken = token.getRefreshToken().getValue();
			}

			jdbcTemplate.update(insertAccessTokenSql, new Object[] { extractTokenKey(token.getValue()),
					new SqlLobValue(serializeAccessToken(token)), authenticationKeyGenerator.extractKey(authentication),
					authentication.isClientOnly() ? null : authentication.getName(),
					authentication.getAuthorizationRequest().getClientId(),
					new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(refreshToken) }, new int[] {
					Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
		}

		public OAuth2AccessToken readAccessToken(String tokenValue) {
			OAuth2AccessToken accessToken = null;

			try {
				accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<OAuth2AccessToken>() {
					public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
						return deserializeAccessToken(rs.getBytes(2));
					}
				}, extractTokenKey(tokenValue));
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find access token for token " + tokenValue);
				}
			}
			catch (IllegalArgumentException e) {
				LOG.warn("Failed to deserialize access token for " + tokenValue);
				removeAccessToken(tokenValue);
			}

			return accessToken;
		}

		public void removeAccessToken(OAuth2AccessToken token) {
			removeAccessToken(token.getValue());
		}

		public void removeAccessToken(String tokenValue) {
			jdbcTemplate.update(deleteAccessTokenSql, extractTokenKey(tokenValue));
		}

		public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
			return readAuthentication(token.getValue());
		}

		public OAuth2Authentication readAuthentication(String token) {
			OAuth2Authentication authentication = null;

			try {
				authentication = jdbcTemplate.queryForObject(selectAccessTokenAuthenticationSql,
						new RowMapper<OAuth2Authentication>() {
							public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
								return deserializeAuthentication(rs.getBytes(2));
							}
						}, extractTokenKey(token));
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find access token for token " + token);
				}
			}
			catch (IllegalArgumentException e) {
				LOG.warn("Failed to deserialize authentication for " + token);
				removeAccessToken(token);
			}

			return authentication;
		}

		public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
			jdbcTemplate.update(insertRefreshTokenSql, new Object[] { extractTokenKey(refreshToken.getValue()),
					new SqlLobValue(serializeRefreshToken(refreshToken)),
					new SqlLobValue(serializeAuthentication(authentication)) }, new int[] { Types.VARCHAR, Types.BLOB,
					Types.BLOB });
		}

		public OAuth2RefreshToken readRefreshToken(String token) {
			OAuth2RefreshToken refreshToken = null;

			try {
				refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {
					public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
						return deserializeRefreshToken(rs.getBytes(2));
					}
				}, extractTokenKey(token));
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find refresh token for token " + token);
				}
			}
			catch (IllegalArgumentException e) {
				LOG.warn("Failed to deserialize refresh token for token " + token);
				removeRefreshToken(token);
			}

			return refreshToken;
		}

		public void removeRefreshToken(OAuth2RefreshToken token) {
			removeRefreshToken(token.getValue());
		}

		public void removeRefreshToken(String token) {
			jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(token));
		}

		public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
			return readAuthenticationForRefreshToken(token.getValue());
		}

		public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
			OAuth2Authentication authentication = null;

			try {
				authentication = jdbcTemplate.queryForObject(selectRefreshTokenAuthenticationSql,
						new RowMapper<OAuth2Authentication>() {
							public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
								return deserializeAuthentication(rs.getBytes(2));
							}
						}, extractTokenKey(value));
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find access token for token " + value);
				}
			}
			catch (IllegalArgumentException e) {
				LOG.warn("Failed to deserialize access token for " + value);
				removeRefreshToken(value);
			}

			return authentication;
		}

		public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
			removeAccessTokenUsingRefreshToken(refreshToken.getValue());
		}

		public void removeAccessTokenUsingRefreshToken(String refreshToken) {
			jdbcTemplate.update(deleteAccessTokenFromRefreshTokenSql, new Object[] { extractTokenKey(refreshToken) },
					new int[] { Types.VARCHAR });
		}

		public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
			List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

			try {
				accessTokens = jdbcTemplate.query(selectAccessTokensFromClientIdSql, new SafeAccessTokenRowMapper(),
						clientId);
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find access token for clientId " + clientId);
				}
			}
			accessTokens = removeNulls(accessTokens);

			return accessTokens;
		}

		public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
			List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();

			try {
				accessTokens = jdbcTemplate.query(selectAccessTokensFromUserNameSql, new SafeAccessTokenRowMapper(),
						userName);
			}
			catch (EmptyResultDataAccessException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Failed to find access token for userName " + userName);
				}
			}
			accessTokens = removeNulls(accessTokens);

			return accessTokens;
		}

		private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
			List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
			for (OAuth2AccessToken token : accessTokens) {
				if (token != null) {
					tokens.add(token);
				}
			}
			return tokens;
		}

		protected String extractTokenKey(String value) {
			if (value == null) {
				return null;
			}
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("MD5");
			}
			catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
			}

			try {
				byte[] bytes = digest.digest(value.getBytes("UTF-8"));
				return String.format("%032x", new BigInteger(1, bytes));
			}
			catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
			}
		}

		private final class SafeAccessTokenRowMapper implements RowMapper<OAuth2AccessToken> {
			public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return deserializeAccessToken(rs.getBytes(2));
				}
				catch (IllegalArgumentException e) {
					String token = rs.getString(1);
					jdbcTemplate.update(deleteAccessTokenSql, token);
					return null;
				}
			}
		}

		protected byte[] serializeAccessToken(OAuth2AccessToken token) {
			return SerializationUtils.serialize(token);
		}

		protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
			return SerializationUtils.serialize(token);
		}

		protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
			return SerializationUtils.serialize(authentication);
		}

		protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
			return SerializationUtils.deserialize(token);
		}

		protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
			return SerializationUtils.deserialize(token);
		}

		protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
			return SerializationUtils.deserialize(authentication);
		}

		public void setInsertAccessTokenSql(String insertAccessTokenSql) {
			this.insertAccessTokenSql = insertAccessTokenSql;
		}

		public void setSelectAccessTokenSql(String selectAccessTokenSql) {
			this.selectAccessTokenSql = selectAccessTokenSql;
		}

		public void setDeleteAccessTokenSql(String deleteAccessTokenSql) {
			this.deleteAccessTokenSql = deleteAccessTokenSql;
		}

		public void setInsertRefreshTokenSql(String insertRefreshTokenSql) {
			this.insertRefreshTokenSql = insertRefreshTokenSql;
		}

		public void setSelectRefreshTokenSql(String selectRefreshTokenSql) {
			this.selectRefreshTokenSql = selectRefreshTokenSql;
		}

		public void setDeleteRefreshTokenSql(String deleteRefreshTokenSql) {
			this.deleteRefreshTokenSql = deleteRefreshTokenSql;
		}

		public void setSelectAccessTokenAuthenticationSql(String selectAccessTokenAuthenticationSql) {
			this.selectAccessTokenAuthenticationSql = selectAccessTokenAuthenticationSql;
		}

		public void setSelectRefreshTokenAuthenticationSql(String selectRefreshTokenAuthenticationSql) {
			this.selectRefreshTokenAuthenticationSql = selectRefreshTokenAuthenticationSql;
		}

		public void setSelectAccessTokenFromAuthenticationSql(String selectAccessTokenFromAuthenticationSql) {
			this.selectAccessTokenFromAuthenticationSql = selectAccessTokenFromAuthenticationSql;
		}

		public void setDeleteAccessTokenFromRefreshTokenSql(String deleteAccessTokenFromRefreshTokenSql) {
			this.deleteAccessTokenFromRefreshTokenSql = deleteAccessTokenFromRefreshTokenSql;
		}

	}