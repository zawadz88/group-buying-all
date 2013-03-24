package pl.edu.pw.eiti.groupbuying.android;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.adapter.OffersExpandableListAdapter;
import pl.edu.pw.eiti.groupbuying.android.adapter.OffersExpandableListAdapter.OfferExpandListGroup;
import pl.edu.pw.eiti.groupbuying.android.api.Address;
import pl.edu.pw.eiti.groupbuying.android.api.Category;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.OfferState;
import pl.edu.pw.eiti.groupbuying.android.api.Seller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MyCouponsActivity extends AbstractGroupBuyingActivity implements OnChildClickListener {
	protected static final String TAG = MyCouponsActivity.class.getSimpleName();
	
	private ExpandableListView offerExpandableListView;
	
	private List<Offer> offers = Arrays
			.asList(new Offer[] { 
					new Offer(1, "XXX1", "lead", "desc", "cond", new Address(), "http://img.gawkerassets.com/img/187xvczebdtjtjpg/original.jpg", 24.0, 48, new Date(), new Date(), new Date(), OfferState.ACTIVE, Category.SHOPPING, new Seller()),
					new Offer(2, "XXX2", "lead", "desc", "cond", new Address(), "http://img.gawkerassets.com/img/187xvczebdtjtjpg/original.jpg", 24.0, 48, new Date(), new Date(), new Date(), OfferState.ACTIVE, Category.SHOPPING, new Seller()),
					new Offer(3, "XXX3", "lead", "desc", "cond", new Address(), "http://img.gawkerassets.com/img/187xvczebdtjtjpg/original.jpg", 24.0, 48, new Date(), new Date(), new Date(), OfferState.ACTIVE, Category.SHOPPING, new Seller()),
					new Offer(4, "XXX4", "lead", "desc", "cond", new Address(), "http://img.gawkerassets.com/img/187xvczebdtjtjpg/original.jpg", 24.0, 48, new Date(), new Date(), new Date(), OfferState.ACTIVE, Category.SHOPPING, new Seller()),
					new Offer(5, "XXX5", "lead", "desc", "cond", new Address(), "http://img.gawkerassets.com/img/187xvczebdtjtjpg/original.jpg", 24.0, 48, new Date(), new Date(), new Date(), OfferState.ACTIVE, Category.SHOPPING, new Seller())
					});
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupons);
		offerExpandableListView = (ExpandableListView) findViewById(R.id.offers);
		OfferExpandListGroup available = new OfferExpandListGroup(getString(R.string.coupon_available_title));
		OfferExpandListGroup used = new OfferExpandListGroup(getString(R.string.coupon_used_title));
		OfferExpandListGroup expired = new OfferExpandListGroup(getString(R.string.coupon_expired_title));
		List<OfferExpandListGroup> groups = Arrays.asList(available, used, expired);
		OffersExpandableListAdapter listAdapter = new OffersExpandableListAdapter(this, groups);
		listAdapter.addItem(offers.get(0), available);
		listAdapter.addItem(offers.get(1), available);
		listAdapter.addItem(offers.get(2), available);
		listAdapter.addItem(offers.get(3), used);
		listAdapter.addItem(offers.get(4), expired);
		offerExpandableListView.setAdapter(listAdapter);
		offerExpandableListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.expander_group));
		offerExpandableListView.expandGroup(0);
		offerExpandableListView.setOnChildClickListener(this);
	}

	@Override
	public void onStart() {
		super.onStart();

		//downloadOffers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.my_coupons_menu, menu);
		
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_offers:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		Offer selectedOffer = offers.get(childPosition);
		Intent intent = new Intent(this, CouponPreviewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("offer", selectedOffer);
		startActivity(intent);
		return true;
	}

}
