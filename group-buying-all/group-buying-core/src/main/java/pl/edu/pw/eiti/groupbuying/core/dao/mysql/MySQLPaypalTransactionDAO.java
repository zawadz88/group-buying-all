package pl.edu.pw.eiti.groupbuying.core.dao.mysql;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.pw.eiti.groupbuying.core.dao.PaypalTransactionDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;

@Repository("paypalTransactionDAO")
@DependsOn(value = "transactionManager")
public class MySQLPaypalTransactionDAO implements PaypalTransactionDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void createTransaction(PaypalTransaction transaction) {
		entityManager.persist(transaction);
		entityManager.flush();
	}

	@Override
	@Transactional
	public int updateTransaction(String transactionToken, TransactionState state) {
		Query query = entityManager.createQuery("update PaypalTransaction t set t.state = :state, t.last_updated = :now where t.transactionToken = :transactionToken");
		query.setParameter("transactionToken", transactionToken);
		query.setParameter("state", state);
		query.setParameter("now", new Date());
		return query.executeUpdate();
	}

}
