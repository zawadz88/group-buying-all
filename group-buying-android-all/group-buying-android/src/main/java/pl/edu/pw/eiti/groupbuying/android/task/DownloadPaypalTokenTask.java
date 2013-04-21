package pl.edu.pw.eiti.groupbuying.android.task;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadPaypalTokenTask extends AbstractGroupBuyingTask<Void> {
	
	private int offerId;
	private String drt;
	private String redirectURL;
	private GroupBuyingApplication application;
	
	public DownloadPaypalTokenTask(int offerId, String drt, AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.offerId = offerId;
		this.drt = drt;
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadPaypalTokenTask clone = new DownloadPaypalTokenTask(offerId, drt, taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		redirectURL = application.getAuthorizedGroupBuyingApi().purchaseOperations().getPaypalRedirectURI(offerId, drt);
	}
	
	public String getRedirectURL() {
		return redirectURL;
	}
	
}
