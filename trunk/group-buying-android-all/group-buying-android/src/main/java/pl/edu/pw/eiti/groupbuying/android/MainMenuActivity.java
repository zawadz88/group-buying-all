package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.adapter.OffersFragmentAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainMenuActivity extends SherlockFragmentActivity {
    protected OffersFragmentAdapter mAdapter;
    protected ViewPager mPager;
    protected PageIndicator mIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAdapter = new OffersFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.offers_fragment_titles));

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(OffersFragmentAdapter.OFFERS_FROM_THE_CITY_FRAGMENT, false);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.offer_list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_coupons:
			break;
		case R.id.options_menu_refresh:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}