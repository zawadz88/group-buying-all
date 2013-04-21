package pl.edu.pw.eiti.groupbuying.core.dao;

import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction;
import pl.edu.pw.eiti.groupbuying.core.domain.PaypalTransaction.TransactionState;

public interface PaypalTransactionDAO {

	void createTransaction(PaypalTransaction transaction);
	
	int updateTransaction(String transactionToken, TransactionState state);
	
}
