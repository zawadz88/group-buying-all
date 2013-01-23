package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.StateEdit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Address;
import pl.edu.pw.eiti.groupbuying.core.domain.Category;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer.State;

@Repository("offerDAO")
public class MySQLOfferDAO implements OfferDAO {
	
	private static final String INSERT_OFFER = "insert into offers(title, description, street, city, postal_code, image_url, category_id, price, start_date, end_date, state, username) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SELECT_CATEGORIES = "SELECT * FROM CATEGORIES";

	private static final String SELECT_OFFERS_BY_USERNAME = "select o.*, c.name as category_name from offers o, categories c where username= ? and state = ? and o.category_id = c.category_id";
	
	private static final String SELECT_OFFERS = "select o.*, c.name as category_name from offers o, categories c where state = ? and o.category_id = c.category_id";

	private static final String SELECT_OFFER_FOR_OFFER_ID = "select o.*, c.name as category_name from offers o, categories c where offer_id = ? and o.category_id = c.category_id";

	private static final String SELECT_USERNAME_FROM_OFFER = "select username from offers where offer_id = ?";
	
	private static final String UPDATE_OFFER = "update offers set title = ?, description = ?, street = ?, city = ?, postal_code = ?, image_url = ?, category_id = ?, price = ?, start_date = ?, end_date = ?, state = ?, username = ? where offer_id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveOffer(Offer offer) {
		jdbcTemplate.update(INSERT_OFFER, new Object[] {offer.getTitle(), offer.getDescription(),
				offer.getAddress().getStreet(), offer.getAddress().getCity(), offer.getAddress().getPostalCode(),
				offer.getImageUrl(), offer.getCategory().getCategoryId(), offer.getPrice(), offer.getStartDate(), offer.getEndDate(), Offer.State.WAITING.getValue(), offer.getUsername()});
		return true;
	}
	
	@Override
	public void updateOffer(Offer offer) {
		jdbcTemplate.update(UPDATE_OFFER, new Object[] {offer.getTitle(), offer.getDescription(),
				offer.getAddress().getStreet(), offer.getAddress().getCity(), offer.getAddress().getPostalCode(),
				offer.getImageUrl(), offer.getCategory().getCategoryId(), offer.getPrice(), offer.getStartDate(), offer.getEndDate(), offer.getState().getValue(), offer.getUsername(), offer.getOfferId()});

		
	}
	
	@Override
	public List<Offer> getActiveOffers() {
		return getOffersForState(Offer.State.ACTIVE);
	}

	@Override
	public List<Offer> getWaitingOffers() {
		return getOffersForState(Offer.State.WAITING);
	}

	@Override
	public List<Offer> getFinishedOffers() {
		return getOffersForState(Offer.State.FINISHED);
	}
	
	private List<Offer> getOffersForState(State state) {
		final List<Offer> offers = new ArrayList<Offer>();
		try {
			jdbcTemplate.query(SELECT_OFFERS, new Object[] {state.getValue()}, new RowCallbackHandler() {
				
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Offer offer = new Offer();
					offer.setOfferId(rs.getInt("offer_id"));
					offer.setTitle(rs.getString("title"));
					offer.setDescription(rs.getString("description"));
					Category category = new Category();
					category.setCategoryId(rs.getInt("category_id"));
					category.setName(rs.getString("category_name"));
					offer.setCategory(category);
					offer.setEndDate(rs.getDate("end_date"));
					offer.setStartDate(rs.getDate("start_date"));
					offer.setImageUrl(rs.getString("image_url"));
					offer.setPrice(rs.getDouble("price"));
					offer.setState(State.getState(rs.getInt("state")));
					offer.setUsername(rs.getString("username"));
					Address address = new Address();
					address.setCity(rs.getString("city"));
					address.setPostalCode(rs.getString("postal_code"));
					address.setStreet(rs.getString("street"));
					offer.setAddress(address);
					offers.add(offer);
				}
			});
		} catch (EmptyResultDataAccessException e) {
			System.err.println("No offers in DB!");
		}
		//TODO obsluga setState
		return offers;
	}
	
	@Override
	public List<Category> getCategories() {
		final List<Category> categories = new ArrayList<Category>();
		try {
			jdbcTemplate.query(SELECT_CATEGORIES, new RowCallbackHandler() {
				
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Category category = new Category();
					category.setCategoryId(rs.getLong("category_id"));
					category.setName(rs.getString("name"));
					categories.add(category);
				}
			});
		} catch (EmptyResultDataAccessException e) {
			System.err.println("No categories in DB!");
		}
		return categories;
	}

	@Override
	public List<Offer> getActiveOffers(String username) {
		return getOffersForState(username, Offer.State.ACTIVE);
	}

	@Override
	public List<Offer> getWaitingOffers(String username) {
		return getOffersForState(username, Offer.State.WAITING);
	}

	@Override
	public List<Offer> getFinishedOffers(final String username) {
		return getOffersForState(username, Offer.State.FINISHED);
	}
	
	private List<Offer> getOffersForState(final String username, final State state) {
		final List<Offer> offers = new ArrayList<Offer>();
		try {
			jdbcTemplate.query(SELECT_OFFERS_BY_USERNAME, new Object[] {username, state.getValue()}, new RowCallbackHandler() {
				
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Offer offer = new Offer();
					offer.setOfferId(rs.getInt("offer_id"));
					offer.setTitle(rs.getString("title"));
					offer.setDescription(rs.getString("description"));
					Category category = new Category();
					category.setCategoryId(rs.getInt("category_id"));
					category.setName(rs.getString("category_name"));
					offer.setCategory(category);
					offer.setEndDate(rs.getDate("end_date"));
					offer.setStartDate(rs.getDate("start_date"));
					offer.setImageUrl(rs.getString("image_url"));
					offer.setPrice(rs.getDouble("price"));
					offer.setState(State.getState(rs.getInt("state")));
					offer.setUsername(rs.getString("username"));
					Address address = new Address();
					address.setCity(rs.getString("city"));
					address.setPostalCode(rs.getString("postal_code"));
					address.setStreet(rs.getString("street"));
					offer.setAddress(address);
					offers.add(offer);
				}
			});
		} catch (EmptyResultDataAccessException e) {
			System.err.println("No offers in DB!");
		}
		//TODO obsluga bledu state
		return offers;
	}

	@Override
	public Offer getOffer(final int offerId) {
		Offer offer = null;
		try{
			offer = jdbcTemplate.queryForObject(SELECT_OFFER_FOR_OFFER_ID, new Object[] {offerId}, new RowMapper<Offer>() {
				public Offer mapRow(ResultSet rs, int arg1) throws SQLException {
					Offer offer = new Offer();
					offer.setOfferId(rs.getInt("offer_id"));
					offer.setTitle(rs.getString("title"));
					offer.setDescription(rs.getString("description"));
					Category category = new Category();
					category.setCategoryId(rs.getInt("category_id"));
					category.setName(rs.getString("category_name"));
					offer.setCategory(category);
					offer.setEndDate(rs.getDate("end_date"));
					offer.setStartDate(rs.getDate("start_date"));
					offer.setImageUrl(rs.getString("image_url"));
					offer.setPrice(rs.getDouble("price"));
					offer.setState(State.getState(rs.getInt("state")));
					offer.setUsername(rs.getString("username"));
					Address address = new Address();
					address.setCity(rs.getString("city"));
					address.setPostalCode(rs.getString("postal_code"));
					address.setStreet(rs.getString("street"));
					offer.setAddress(address);
					return offer;
				};
			}
			);
			
		} catch (EmptyResultDataAccessException e) {
			System.err.println("No offer for offerId: " + offerId);
		}
		return offer;
	}

	@Override
	public String getUsernameForOffer(int offerId) {
		String username = null;
		try {
			username = jdbcTemplate.queryForObject(SELECT_USERNAME_FROM_OFFER, new Object [] {offerId}, String.class);
		} catch(EmptyResultDataAccessException e) {
			System.err.println("No username for offerId: " + offerId);
		}
		return username;
	}

}
