package pl.edu.pw.eiti.groupbuying.android.task;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;

public class DownloadCouponListTask extends AbstractGroupBuyingTask<Void> {
	
	private Coupon [] couponList;
	private GroupBuyingApplication application;
	
	public DownloadCouponListTask(AsyncTaskListener listener, GroupBuyingApplication application) {
		super();
		this.taskListener = listener;
		this.application = application;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		DownloadCouponListTask clone = new DownloadCouponListTask(taskListener, application);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		couponList = application.getAuthorizedGroupBuyingApi().couponOperations().getCoupons();
	}
	
	public Coupon [] getCouponList() {
		return couponList;
	}
}
