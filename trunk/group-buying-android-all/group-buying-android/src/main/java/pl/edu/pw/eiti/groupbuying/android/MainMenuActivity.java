package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.adapter.OffersFragmentAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainMenuActivity extends FragmentActivity {
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
}