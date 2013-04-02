package pl.edu.pw.eiti.groupbuying.android.task;

import java.util.ArrayList;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadCitiesTask extends AbstractGroupBuyingTask<Void> {

	private ArrayList<City> cities;
	private GroupBuyingApplication application;
	
	public DownloadCitiesTask(AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadCitiesTask clone = new DownloadCitiesTask(taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		cities = application.getUnauthorizedGroupBuyingApi().cityOperations().getCities();
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}
	
}
