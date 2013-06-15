package pl.edu.pw.eiti.groupbuying.partner.android.task;

import pl.edu.pw.eiti.groupbuying.partner.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.partner.android.api.ClaimResponse;
import pl.edu.pw.eiti.groupbuying.partner.android.api.CouponInfo;
import pl.edu.pw.eiti.groupbuying.partner.android.task.util.AsyncTaskListener;

public class ClaimCouponTask extends AbstractGroupBuyingTask<Void> {
	
	private GroupBuyingApplication application;
	private CouponInfo couponInfo;
	private ClaimResponse claimResponse;
	
	public ClaimCouponTask(AsyncTaskListener listener, GroupBuyingApplication application, CouponInfo couponInfo) {
		super();
		this.taskListener = listener;
		this.application = application;
		this.couponInfo = couponInfo;
	}
	
	@Override
	public AbstractGroupBuyingTask<?> getClone() {
		ClaimCouponTask clone = new ClaimCouponTask(taskListener, application, couponInfo);
		return clone;
	}
	
	@Override
	protected void doInBackgroundSafe() throws Exception {
		claimResponse = application.getAuthorizedGroupBuyingApi().couponOperations().claimCoupon(couponInfo);
	}
	
	public ClaimResponse getClaimResponse() {
		return claimResponse;
	}
	
}
