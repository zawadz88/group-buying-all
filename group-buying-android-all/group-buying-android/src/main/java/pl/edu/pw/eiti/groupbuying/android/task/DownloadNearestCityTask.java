package pl.edu.pw.eiti.groupbuying.android.task;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadNearestCityTask extends AbstractGroupBuyingTask<Void> {

	private double latitude;
	private double longitude;
	private City city;
	private GroupBuyingApplication application;
	
	public DownloadNearestCityTask(double lat, double lon, AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.latitude = lat;
		this.longitude = lon;
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadNearestCityTask clone = new DownloadNearestCityTask(latitude, longitude, taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		city = application.getUnauthorizedGroupBuyingApi().cityOperations().getNearestCity(latitude, longitude);
	}
	
	public City getCity() {
		return city;
	}
	
}
