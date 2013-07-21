/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.spatial.impl.DistanceSortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.OfferDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;

@Repository("offerDAO")
public class MySQLOfferDAO implements OfferDAO {

	private static final String SELECT_USERNAME_FROM_OFFER = "select username from offers where offer_id = ?";
	
	private static final String UPDATE_SOLD_COUNT_INCREMENT = "update offers set sold_count = sold_count + 1 where offer_id = ?";
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager entityManager;

	public FullTextEntityManager getFullTextEntityManager(){
		return Search.getFullTextEntityManager(entityManager);
	}
	
	@Override
	@Transactional
	public boolean saveOffer(final Offer offer) {
		entityManager.persist(offer);
		return true;
	}
	
	@Override
	@Transactional
	public void updateOffer(final Offer offer) {
		entityManager.merge(offer);
	}
	
	@Override
	public List<Offer> getActiveOffers() {
		return getOffersForState(OfferState.ACTIVE);
	}

	@Override
	public List<Offer> getWaitingOffers() {
		return getOffersForState(OfferState.WAITING);
	}

	@Override
	public List<Offer> getFinishedOffers() {
		return getOffersForState(OfferState.FINISHED);
	}

	@Transactional
	private List<Offer> getOffersForState(OfferState state) {
		TypedQuery<Offer> query = entityManager.createQuery("select o from Offer o where o.state = :state", Offer.class);
		query.setParameter("state", state);

		return query.getResultList();
	}
	
	@Override
	public List<Offer> getActiveOffers(String username) {
		return getOffersForState(username, OfferState.ACTIVE);
	}

	@Override
	public List<Offer> getWaitingOffers(String username) {
		return getOffersForState(username, OfferState.WAITING);
	}

	@Override
	public List<Offer> getFinishedOffers(final String username) {
		return getOffersForState(username, OfferState.FINISHED);
	}

	@Transactional
	private List<Offer> getOffersForState(final String username, final OfferState state) {
		TypedQuery<Offer> query = entityManager.createQuery("select o from Offer o where o.state = :state and o.seller.email = :username", Offer.class);
		query.setParameter("state", state);
		query.setParameter("username", username);

		return query.getResultList();
	}

	@Override
	@Transactional
	public Offer getOffer(final int offerId) {
		Offer offer = entityManager.find(Offer.class, offerId);		
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

	@Override
	public List<OfferEssentialDTO> getOfferEssentialsByCategoryAndPageNumber(final Category category, final OfferState state, final int pageNumber, final int pageSize) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();		
	
		CriteriaQuery<OfferEssentialDTO> c = qb.createQuery(OfferEssentialDTO.class);
		Root<Offer> p = c.from(Offer.class);
		Predicate stateCondition = qb.equal(p.get("state"), state);
		if(category != null) {
			Predicate categoryCondition = qb.equal(p.get("category"), category);
			c.where(qb.and(stateCondition, categoryCondition));			
		} else {
			c.where(stateCondition);		
		}

		c.multiselect(p.get("offerId"), p.get("title"), p.get("imageUrl"), p.get("price"), p.get("priceBeforeDiscount"), p.get("startDate"), p.get("endDate"), p.get("category"), p.get("latitude"), p.get("longitude"), p.get("soldCount"));
		TypedQuery<OfferEssentialDTO> query = entityManager.createQuery(c); 
		query.setHint("org.hibernate.cacheable", true);
		query.setFirstResult(pageNumber * pageSize);
		query.setMaxResults(pageSize);
		List<OfferEssentialDTO> result = query.getResultList();
		
		return result;
	}
	
	@Override
	public List<OfferEssentialDTO> getOfferEssentialsByCityAndPageNumber(final String cityId, final OfferState state, final int pageNumber, final int pageSize) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();		
	
		CriteriaQuery<OfferEssentialDTO> c = qb.createQuery(OfferEssentialDTO.class);
		Root<Offer> p = c.from(Offer.class);
		Predicate stateCondition = qb.equal(p.get("state"), state);
		
		Predicate cityCondition = qb.equal(p.get("city").get("cityId"), cityId);
		Predicate categoryCondition = qb.equal(p.get("category"), Category.CITY);
		c.where(qb.and(categoryCondition, qb.and(stateCondition, cityCondition)));			

		c.multiselect(p.get("offerId"), p.get("title"), p.get("imageUrl"), p.get("price"), p.get("priceBeforeDiscount"), p.get("startDate"), p.get("endDate"), p.get("category"), p.get("latitude"), p.get("longitude"), p.get("soldCount"));
		TypedQuery<OfferEssentialDTO> query = entityManager.createQuery(c); 
		query.setHint("org.hibernate.cacheable", true);
		query.setFirstResult(pageNumber * pageSize);
		query.setMaxResults(pageSize);
		List<OfferEssentialDTO> result = query.getResultList();
		
		return result;
	}
	
	@Override
	@Transactional
	public List<OfferEssentialDTO> getClosestOffers(double latitude, double longitude, double searchRadius) {
		QueryBuilder builder = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(Offer.class).get();
		Query luceneQuery = builder.bool()
				.must(builder.spatial().onCoordinates("loc").within(searchRadius, Unit.KM).ofLatitude(latitude).andLongitude(longitude).createQuery())
				.must(builder.keyword().onField("state").matching(OfferState.ACTIVE).createQuery())
			.createQuery();
	
		FullTextQuery hibQuery = getFullTextEntityManager().createFullTextQuery(luceneQuery, Offer.class);
		hibQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.QUERY);
		Sort distanceSort = new Sort(new DistanceSortField(latitude, longitude, "loc"));
		hibQuery.setSort(distanceSort);
		List<?> results = hibQuery.getResultList();
		List<OfferEssentialDTO> offers = new ArrayList<OfferEssentialDTO>();
		if (results != null) {
			for(Object offer : results) {
				OfferEssentialDTO offerEssentialDTO = ((Offer) offer).getOfferEssentialDTO();
				offers.add(offerEssentialDTO);
			}
		}
		return offers;
	}
	
	@Override
	@Transactional
	public int indexOffers() {
		int counter = 0;
		TypedQuery<Offer> query = entityManager.createQuery("from Offer", Offer.class);
		List<Offer> offers = query.getResultList();
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
		for (Offer offer : offers) {
			if(offer.getLatitude() != null && offer.getLongitude() != null) {
				fullTextEntityManager.index(offer);
				counter++;
			}
		}
		fullTextEntityManager.getSearchFactory().optimize(Offer.class);
		fullTextEntityManager.flushToIndexes();
		return counter;
	}

	@Override
	@Transactional
	public void incrementSoldCount(int offerId) {
		jdbcTemplate.update(UPDATE_SOLD_COUNT_INCREMENT, offerId);		
	}
}
