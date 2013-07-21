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

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.CouponDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Coupon;

@Repository("couponDAO")
public class MySQLCouponDAO implements CouponDAO {
			
	@PersistenceContext
	private EntityManager entityManager;

	public FullTextEntityManager getFullTextEntityManager(){
		return Search.getFullTextEntityManager(entityManager);
	}
	
	@Override
	@Transactional
	public boolean persistCoupon(Coupon coupon) {
		entityManager.persist(coupon);
		return true;
	}

	@Override
	@Transactional
	public boolean updateCoupon(Coupon coupon) {
		entityManager.merge(coupon);
		return true;
	}

	@Override
	@Transactional
	public List<Coupon> getCouponsForClientId(final String email) {
		TypedQuery<Coupon> query = entityManager.createQuery("select c from Coupon c where c.client.email = :email", Coupon.class);
		query.setParameter("email", email);
		return query.getResultList();
	}

	@Override
	@Transactional
	public Coupon getCoupon(final int couponId) {
		Coupon coupon = entityManager.find(Coupon.class, couponId);		
		return coupon;
	}

}
