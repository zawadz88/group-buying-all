package pl.edu.pw.eiti.groupbuying.android;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.adapter.OffersExpandableListAdapter;
import pl.edu.pw.eiti.groupbuying.android.adapter.OffersExpandableListAdapter.OfferExpandListGroup;
import pl.edu.pw.eiti.groupbuying.android.api.Address;
import pl.edu.pw.eiti.groupbuying.android.api.Category;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.Offer.State;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MyCouponsActivity extends AbstractGroupBuyingActivity implements OnChildClickListener {
	protected static final String TAG = MyCouponsActivity.class.getSimpleName();
	
	private ExpandableListView offerExpandableListView;
	
	private List<Offer> offers = Arrays
			.asList(new Offer[] {
					new Offer(
							0,
							"Super lightbulbs",
							"Integer rhoncus sodales augue in adipiscing.",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur iaculis leo eget mauris gravida placerat a eu libero. Fusce rutrum tincidunt nibh non rhoncus. Quisque viverra pretium aliquam. Praesent eu pretium orci. Nulla erat orci, luctus sed facilisis ac, faucibus et lorem. Phasellus malesuada mauris at sapien hendrerit consequat ac nec eros. Donec sodales urna quis turpis gravida quis elementum velit fermentum. Pellentesque luctus aliquet nisi, non condimentum neque malesuada id. Nullam magna neque, porta eu tincidunt id, ultrices eu dui. Suspendisse gravida velit et nisi sodales vitae auctor mi porttitor. Curabitur hendrerit dictum purus a rhoncus.",
							new Address(),
							"http://bi.gazeta.pl/im/0a/ac/cc/z13413386F,Agnieszka-Radwanska.jpg",
							150, 300, new Date(), new Date(), State.ACTIVE,
							new Category(), "BOSCH"),
					new Offer(
							1,
							"Nauka języków",
							"ciekawy lead",
							"Integer ac erat a velit ultricies accumsan nec ut tortor. Phasellus congue mi sit amet dui viverra malesuada. Donec feugiat vestibulum diam non suscipit. Aliquam tempus, metus sed tincidunt suscipit, leo massa sagittis dui, non commodo enim dolor nec quam. Fusce fermentum egestas pellentesque. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Curabitur laoreet elementum mattis. Phasellus suscipit, justo id tempus tristique, lacus risus blandit nunc, non malesuada orci risus vel lectus. Vestibulum ac purus neque. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Etiam in purus purus, vel condimentum enim. Vestibulum tempor fermentum ante, quis laoreet velit molestie ac.",
							new Address(),
							"http://static.pl.groupon-content.net/27/39/1361209483927.jpg",
							150, 300, new Date(), new Date(), State.ACTIVE,
							new Category(), "BOSCH"),
					new Offer(
							2,
							"Super iPad",
							"ciekawy lead",
							"Suspendisse a accumsan lorem. Curabitur pulvinar interdum urna, et luctus dolor pellentesque eget. Aliquam et justo eu magna imperdiet lacinia nec ut enim. Curabitur id turpis eget risus congue elementum. Curabitur vestibulum est a neque eleifend pretium. Curabitur ut erat dui, et vulputate lacus. Mauris id massa elit. Praesent ullamcorper consectetur fermentum. Etiam feugiat sollicitudin nibh in varius. In consequat, lacus ac pellentesque fermentum, orci mauris molestie nisi, fermentum semper leo lacus eu tellus. Aliquam laoreet sem at nisi facilisis et interdum dolor suscipit. Aenean interdum rutrum lacus sed lobortis. Proin tempor rutrum justo, eget lobortis purus condimentum non.",
							new Address(),
							"http://static.pl.groupon-content.net/55/37/1361292093755.jpg",
							150, 300, new Date(), new Date(), State.ACTIVE,
							new Category(), "BOSCH"),
					new Offer(
							3,
							"Super laptop",
							"ciekawy lead",
							"ciekawy opis",
							new Address(),
							"http://static.pl.groupon-content.net/25/83/1361446708325.jpg",
							150, 300, new Date(), new Date(), State.ACTIVE,
							new Category(), "BOSCH"),
					new Offer(
							4,
							"Super garnki",
							"ciekawy lead",
							"ciekawy opis",
							new Address(),
							"http://static.pl.groupon-content.net/63/61/1360919626163.jpg",
							150, 300, new Date(), new Date(), State.ACTIVE,
							new Category(), "BOSCH") });
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

	private void downloadOffers() {
		new DownloadOfferTask().execute();
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadOfferTask extends AsyncTask<Void, Void, Offer> {

		private Exception exception;

		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}

		@Override
		protected Offer doInBackground(Void... params) {
			try {
				return getApplicationContext().getGroupBuyingApi()
						.offerOperations().getOfferById(13);
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Offer result) {
			dismissProgressDialog();
			processException(exception);
			if (result != null) {
				// aq.id(R.id.offer).text(result.toString());
			}
		}
	}

}
