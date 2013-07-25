package pl.edu.pw.eiti.groupbuying.core.dao;

import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;

/**
 * DAO for operations on {@link PaypalTransaction} entities
 * @author Piotr Zawadzki
 *
 */
public interface PaypalTransactionDAO {

	/**
	 * Creates a transaction
	 * @param transaction
	 */
	void createTransaction(PaypalTransaction transaction);
	
	/**
	 * Updates transaction status
	 * @param transactionToken transaction token
	 * @param state new transaction state
	 * @return
	 */
	int updateTransaction(String transactionToken, TransactionState state);
	
	/**
	 * Gets a transaction for a given transaction token
	 * @param transactionToken
	 * @return
	 */
	PaypalTransaction getTransaction(String transactionToken);
	
}
