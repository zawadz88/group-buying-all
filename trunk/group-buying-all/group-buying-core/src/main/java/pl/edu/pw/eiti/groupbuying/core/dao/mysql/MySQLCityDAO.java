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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.spatial.impl.DistanceSortField;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.CityDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.City;
import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;

@Repository("cityDAO")
public class MySQLCityDAO implements CityDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public FullTextEntityManager getFullTextEntityManager() {
		FullTextEntityManager fullTextEntityManager;
		fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		return fullTextEntityManager;
	}

	@Override
	@Transactional
	public List<CityDTO> getCities() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CityDTO> c = qb.createQuery(CityDTO.class);
		Root<City> p = c.from(City.class);
		c.multiselect(p.get("cityId"), p.get("name"), p.get("latitude"), p.get("longitude"));
		TypedQuery<CityDTO> query = entityManager.createQuery(c);
		List<CityDTO> result = query.getResultList();
		return result;
	}

	@Override
	@Transactional
	public CityDTO getClosestCity(double latitude, double longitude) {		
		QueryBuilder builder = getFullTextEntityManager().getSearchFactory().buildQueryBuilder().forEntity(City.class).get();
		Query luceneQuery = builder.spatial().onCoordinates("loc").within(500, Unit.KM).ofLatitude(latitude).andLongitude(longitude).createQuery();

		FullTextQuery hibQuery = getFullTextEntityManager().createFullTextQuery(luceneQuery, City.class);
		Sort distanceSort = new Sort(new DistanceSortField(latitude, longitude, "loc"));
		hibQuery.setSort(distanceSort);
		hibQuery.setMaxResults(1);
		List<?> results = hibQuery.getResultList();
		CityDTO cityDTO = null;
		if (results != null && !results.isEmpty()) {
			City city = (City) results.get(0);
			cityDTO = city.getCityDTO();
		}
		return cityDTO;
	}

	@Override
	@Transactional
	public void indexCities() {

		TypedQuery<City> query = entityManager.createQuery("from City", City.class);
		List<City> cities = query.getResultList();

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		for (City city : cities) {
			fullTextEntityManager.index(city);
		}
		fullTextEntityManager.getSearchFactory().optimize(City.class);
	}

}
