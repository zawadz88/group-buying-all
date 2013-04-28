package pl.edu.pw.eiti.groupbuying.android.adapter;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;

public class CouponsExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private List<CouponExpandListGroup> groups;

	public CouponsExpandableListAdapter(Activity activity, List<CouponExpandListGroup> groups) {
		this.activity = activity;
		this.groups = groups;
	}

	public void addItem(Coupon item, CouponExpandListGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		List<Coupon> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
	}
	
	@Override
	public Coupon getChild(int groupPosition, int childPosition) {
		List<Coupon> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		Coupon child = getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) activity
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			 view = infalInflater.inflate(R.layout.coupon_row, null);
		}
		AQuery aq = new AQuery(activity, view);

		aq.id(R.id.offerImage).image(child.getOffer().getImageUrl());
		aq.id(R.id.offerTitle).text(child.getOffer().getTitle());
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		List<Coupon> chList = groups.get(groupPosition).getItems();

		return chList.size();

	}

	@Override
	public CouponExpandListGroup getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		CouponExpandListGroup group = getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.coupon_category_row, null);
		}
		TextView categoryTitle = (TextView) view.findViewById(R.id.categoryName);
		categoryTitle.setText(group.getName());
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	public static class CouponExpandListGroup {

		private String name;

		private List<Coupon> items = new ArrayList<Coupon>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Coupon> getItems() {
			return items;
		}

		public void setItems(List<Coupon> items) {
			this.items = items;
		}
		
		public CouponExpandListGroup(String name) {
			super();
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if(o != null && o instanceof CouponExpandListGroup) {
				return this.name.equals(((CouponExpandListGroup) o).getName());
			}
			return false;
		}

	}

}
