package pl.edu.pw.eiti.groupbuying.android.task;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadDefaultCityTask extends AbstractGroupBuyingTask<Void> {

	private City city;
	private GroupBuyingApplication application;
	
	public DownloadDefaultCityTask(AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadDefaultCityTask clone = new DownloadDefaultCityTask(taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		city = application.getUnauthorizedGroupBuyingApi().cityOperations().getDefaultCity();
	}
	
	public City getCity() {
		return city;
	}
	
}
