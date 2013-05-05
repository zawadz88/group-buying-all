package pl.edu.pw.eiti.groupbuying.android.fragment;

import java.util.Arrays;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.CouponPreviewActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.adapter.CouponsExpandableListAdapter;
import pl.edu.pw.eiti.groupbuying.android.adapter.CouponsExpandableListAdapter.CouponExpandListGroup;
import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.androidquery.AQuery;

public class CouponListFragment extends Fragment implements OnChildClickListener {
	
	private Coupon [] coupons;
	private ExpandableListView couponExpandableListView;
	
	public static CouponListFragment newInstance(Coupon [] coupons) {
		CouponListFragment fragment = new CouponListFragment();
		fragment.coupons = coupons;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null && savedInstanceState.getSerializable("coupons") != null) {
			//offer = (Offer) savedInstanceState.getSerializable("offer");
		}
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_coupon_list,	container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		couponExpandableListView = aq.id(R.id.offers).getExpandableListView();
		CouponExpandListGroup available = new CouponExpandListGroup(getString(R.string.coupon_available_title));
		CouponExpandListGroup used = new CouponExpandListGroup(getString(R.string.coupon_used_title));
		CouponExpandListGroup expired = new CouponExpandListGroup(getString(R.string.coupon_expired_title));
		List<CouponExpandListGroup> groups = Arrays.asList(available, used, expired);
		CouponsExpandableListAdapter listAdapter = new CouponsExpandableListAdapter(getActivity(), groups);
		
		for(Coupon coupon : coupons) {
			switch (coupon.getCouponState()) {
			case BOUGHT:
				listAdapter.addItem(coupon, available);				
				break;
			case EXPIRED:			
				listAdapter.addItem(coupon, expired);		
				break;
			case REDEEMED:		
				listAdapter.addItem(coupon, used);				
				break;
			default:
				break;
			}
		}
		couponExpandableListView.setAdapter(listAdapter);
		couponExpandableListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.expander_group));
		couponExpandableListView.expandGroup(0);
		couponExpandableListView.setOnChildClickListener(this);

		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("offer", offer);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		Coupon selectedCoupon = coupons[childPosition];
		Intent intent = new Intent(getActivity(), CouponPreviewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("coupon", selectedCoupon);
		startActivity(intent);
		return true;
	}
}
