package pl.edu.pw.eiti.groupbuying.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "paypal_transactions")
public class PaypalTransaction {

	@Id
	@Column(name = "id")
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "offer_id")
	private Offer offer;

	@Column(name = "transaction_token")
	private String transactionToken;

	@Column(name = "state")
	@Enumerated(EnumType.ORDINAL)
	private TransactionState state;

	@Column(name = "created")
	private Date created;

	@Column(name = "last_updated")
	private Date lastUpdated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public String getTransactionToken() {
		return transactionToken;
	}

	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}

	public TransactionState getState() {
		return state;
	}

	public void setState(TransactionState state) {
		this.state = state;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "PaypalTransaction [id=" + id + ", client=" + client + ", offer=" + offer + ", transactionToken=" + transactionToken + ", state=" + state + ", created=" + created + ", lastUpdated=" + lastUpdated + "]";
	}

	public static enum TransactionState {
		STARTED, CANCELLED, COMPLETED, EXPIRED;
	}
}
