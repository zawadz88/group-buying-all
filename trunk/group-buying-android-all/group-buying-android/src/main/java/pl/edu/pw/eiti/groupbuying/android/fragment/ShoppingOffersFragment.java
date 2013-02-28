package pl.edu.pw.eiti.groupbuying.android.fragment;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.OfferActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.adapter.OfferListAdapter;
import pl.edu.pw.eiti.groupbuying.android.api.Address;
import pl.edu.pw.eiti.groupbuying.android.api.Category;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.Offer.State;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.androidquery.AQuery;

public final class ShoppingOffersFragment extends AbstractListFragment {

	private List<Offer> offerList = Arrays
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

	public static ShoppingOffersFragment newInstance(String content) {
		ShoppingOffersFragment fragment = new ShoppingOffersFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if ((savedInstanceState != null) &&
		// savedInstanceState.containsKey(KEY_CONTENT)) { }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_basic_offers,
				container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		listView = aq.id(android.R.id.list).getListView();
		loadingView = aq.id(R.id.list_loading).getProgressBar();
		emptyView = aq.id(R.id.list_empty).getTextView();

		listView.setBackgroundResource(android.R.color.white);
		//listView.setDivider(getResources().getDrawable(android.R.color.white));
		//listView.setDividerHeight(1 * (int) (getResources().getDisplayMetrics().density + 0.5f));

		listView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (offerList != null
				&& offerList.size() > 0) {
			// Set new adapter
			setListAdapter(new OfferListAdapter(getActivity(), 0, offerList));
			setListViewState(ListViewState.CONTENT);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Offer selectedOffer = offerList.get(position);
		Intent intent = new Intent(getActivity(), OfferActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("offer", selectedOffer);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub

	}
}
