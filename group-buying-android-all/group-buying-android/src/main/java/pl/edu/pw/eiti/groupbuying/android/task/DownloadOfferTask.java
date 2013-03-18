package pl.edu.pw.eiti.groupbuying.android.task;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadOfferTask extends AbstractGroupBuyingTask<Void> {
	
	private int offerId;
	private Offer offer;
	private GroupBuyingApplication application;
	
	public DownloadOfferTask(int offerId, AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.offerId = offerId;
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadOfferTask clone = new DownloadOfferTask(offerId, taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		offer = application.getUnauthorizedGroupBuyingApi().offerOperations().getOfferById(offerId);
	}
	
	public Offer getOffer() {
		return offer;
	}
	
}
