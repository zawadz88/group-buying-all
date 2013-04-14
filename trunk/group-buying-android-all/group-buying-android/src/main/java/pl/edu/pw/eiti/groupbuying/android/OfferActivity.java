/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.android;

import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.fragment.LoadingFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.NoInternetFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.OfferFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.android.util.NetUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class OfferActivity extends AbstractGroupBuyingActivity implements AsyncTaskListener, NoInternetListener {

	protected static final String TAG = OfferActivity.class.getSimpleName();
    private static final int LOADING = 0;
    private static final int OFFER = 1;
    private static final int NO_INTERNET = 2;
    private static final int FRAGMENT_COUNT = NO_INTERNET + 1;
    private static final String FRAGMENT_PREFIX = "fragment";
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    
    private boolean restoredFragment = false;
	private ShareActionProvider actionProvider;
	
	private Offer offer;	
	private int offerId;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer);
		offerId = -1;
		if (getIntent().getSerializableExtra("offerId") != null) {
			offerId = getIntent().getIntExtra("offerId", -1);
			if(offerId == -1) {
				finish();
				return;
			}
		}
		if (savedInstanceState != null && savedInstanceState.getSerializable("offer") != null) {
			offer = (Offer) savedInstanceState.getSerializable("offer");
	        setTitle(offer.getTitle());
		}
		
		for(int i = 0; i < fragments.length; i++) {
            restoreFragment(savedInstanceState, i);
        }
		
		if(offer == null) {
			new DownloadOfferTask(offerId, this, getApplicationContext()).execute();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.offer_menu, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_item_share);
	    actionProvider = (ShareActionProvider) actionItem.getActionProvider();
		
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
		case R.id.options_menu_coupons:
			Intent intent = new Intent(this, MyCouponsIntermediateActivity.class);
			this.startActivity(intent);
			break;
		case R.id.options_menu_offers:
			break;
		default:
			if(offer == null) {//do not share if offer is not initialized
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
		}
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("offerId", offerId);
		outState.putSerializable("offer", offer);
		FragmentManager manager = getSupportFragmentManager();
		// Since we're only adding one Fragment at a time, we can only save one.
		Fragment f = manager.findFragmentById(R.id.body_frame);
		for (int i = 0; i < fragments.length; i++) {
			if (fragments[i] == f) {
				manager.putFragment(outState, getBundleKey(i), fragments[i]);
			}
		}
	}
	
	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		if(result.equals(TaskResult.SUCCESSFUL)) {
			Offer downloadedOffer = ((DownloadOfferTask) task).getOffer();
			if(downloadedOffer == null) {
				//TODO show error stub?
			} else {
				offer = downloadedOffer;
		        setTitle(offer.getTitle());
		        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
			    
			    Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, offer.getTitle());
				//TODO zastapic jakims realnym linkiem
				shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");			    
			    actionProvider.setShareIntent(shareIntent);
			    showFragment(OFFER);
			}
		} else if(result.equals(TaskResult.FAILED)) {
			Exception exception = task.getException();
			if(exception != null) {
				if(exception instanceof HttpClientErrorException || exception instanceof DuplicateConnectionException || exception instanceof ResourceAccessException) {
					showFragment(NO_INTERNET);
				} else {
					//TODO rozroznic, zrobic rzucanie błędów po stronie serwera
					showFragment(NO_INTERNET);
				}
			}
		}
		
	}
	
	@Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (restoredFragment) {
            return;
        }
        if(offer != null) {
        	showFragment(OFFER);	
        } else {
            if(NetUtils.isOnline(this)) {
    			showFragment(LOADING);	
            } else {
    			showFragment(NO_INTERNET);	
            }
        }        
    }
	
	private void restoreFragment(Bundle savedInstanceState, int fragmentIndex) {
		Fragment fragment = null;
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            fragment = manager.getFragment(savedInstanceState, getBundleKey(fragmentIndex));
        }
        if (fragment != null) {
            fragments[fragmentIndex] = fragment;
            if(fragmentIndex == NO_INTERNET) {
            	((NoInternetFragment)fragment).setListener(this);
            }
            restoredFragment = true;
        } else {
            switch (fragmentIndex) {
                case OFFER:
                	if(offer != null) {
                        fragments[OFFER] = OfferFragment.newInstance(offer);                		
                	}
                    break;
                case NO_INTERNET:
                    fragments[NO_INTERNET] = NoInternetFragment.newInstance(this);
                    break;
                case LOADING:
                    fragments[LOADING] = LoadingFragment.newInstance(getString(R.string.loading_offer_message));
                    break;
                default:
                    Log.w(TAG, "OfferActivity: invalid fragment index: " + fragmentIndex);
                    break;
            }
        }
	}
	
	public void showFragment(int fragmentNo) {
		if(fragmentNo == OFFER && fragments[OFFER] == null) {
			fragments[OFFER] = OfferFragment.newInstance(offer);
		}
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.body_frame, fragments[fragmentNo]).commit();
	}
	
    private String getBundleKey(int index) {
        return FRAGMENT_PREFIX + Integer.toString(index);
    }

	@Override
	public void onDeviceOnline() {
		showFragment(LOADING);
		new DownloadOfferTask(offerId, this, getApplicationContext()).execute();		
	}

	@Override
	public void onDeviceOffline() {
		// TODO Auto-generated method stub
		
	}
}
