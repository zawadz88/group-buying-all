package pl.edu.pw.eiti.groupbuying.android.task;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadOfferListTask extends AbstractGroupBuyingTask<Void> {
	
	private String category;
	private int pageNumber;
	private List<OfferEssential> offerList;
	private GroupBuyingApplication application;
	
	public DownloadOfferListTask(String category, int pageNumber, AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.category = category;
		this.pageNumber = pageNumber;
		this.taskListener = listener;
		this.application = application;
	}

	@Override
	protected void doInBackgroundSafe() throws Exception {
		offerList = application.getUnauthorizedGroupBuyingApi().offerOperations().getOffers(category, pageNumber);
	}
	
	public List<OfferEssential> getOfferList() {
		return offerList;
	}
}
