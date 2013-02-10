package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.adapter.TestFragmentAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.PageIndicator;

public abstract class BaseSampleActivity extends FragmentActivity {
    
    protected TestFragmentAdapter mAdapter;
    protected ViewPager mPager;
    protected PageIndicator mIndicator;

}
