package pl.edu.pw.eiti.groupbuying.android.task;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.CityConfig;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadCityConfigTask extends AbstractGroupBuyingTask<Void> {

	private Double latitude;
	private Double longitude;

	private CityConfig cityConfig;
	private GroupBuyingApplication application;

	public DownloadCityConfigTask(Double lat, Double lon, AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.latitude = lat;
		this.longitude = lon;
		this.taskListener = listener;
		this.application = application;
	}

	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadCityConfigTask clone = new DownloadCityConfigTask(latitude, longitude, taskListener, application);
		return clone;
	}

	@Override
	protected void doInBackgroundSafe() throws Exception {
		cityConfig = application.getUnauthorizedGroupBuyingApi().cityOperations().getCityConfig(latitude, longitude);
	}

	public CityConfig getCityConfig() {
		return cityConfig;
	}

}
