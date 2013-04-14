package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.PaymentConfirmedActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidquery.AQuery;
import com.paypal.android.MECL.PayPal;
import com.paypal.android.MECL.PayPalListener;

public class PayPalPaymentFragment extends AbstractPaymentFragment implements PayPalListener {
	
	private Offer offer;
	private WebView payPalWebView;

	/**
	 *  The PayPal server to be used - can be ENV_SANDBOX, ENV_NONE and ENV_LIVE
	 */
	private static final int PAYPAL_SERVER = PayPal.ENV_SANDBOX;
	
	/**
	 *  The reference token that we get from initializing the MECL library
	 */
	public String deviceReferenceToken;
	
	/**
	 * The popup to show loading when initializing the library
	 */
	private ProgressDialog progressDialog;
	
	public static PayPalPaymentFragment newInstance(Offer offer) {
		PayPalPaymentFragment fragment = new PayPalPaymentFragment();
		fragment.offer = offer;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null && savedInstanceState.getSerializable("offer") != null) {
			offer = (Offer) savedInstanceState.getSerializable("offer");
		}
		// Create a separate thread to do the initialization
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_paypal_payment, container, false);
	    AQuery aq = new AQuery(getActivity(), rootView);
	    payPalWebView = aq.id(R.id.payPalWebView).getWebView();
	    progressDialog = ProgressDialog.show(activity, "", "Loading");
	    Thread libraryInitializationThread = new Thread() {
			public void run() {
				// Initialize the library
				initLibrary();
				// The library is initialized so let's launch it by notifying
				// our handler
				if(activity != null) {
					if (PayPal.getInstance().isLibraryInitialized()) {
						activity.runOnUiThread(new Runnable() {								
							@Override
							public void run() {
								launchPayment();
							}
						});
					} else {
						activity.finish();
					}
				}						
			}
		};
		libraryInitializationThread.start();
	    return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		payPalWebView.setWebViewClient(null);
		payPalWebView = null;
		if(progressDialog != null) {
			progressDialog.cancel();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("offer", offer);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void launchPayment() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(getString(R.string.base_url));
    	sb.append("paypal/mecl/checkout/");
    	sb.append(offer.getOfferId());
	    sb.append("?drt=").append(deviceReferenceToken);
	    	   
	    payPalWebView.getSettings().setJavaScriptEnabled(true);;
	    //Setup a WebViewClient so we know when the url changes
	    payPalWebView.setWebViewClient(new WebViewClient() {
	    	public void onPageFinished(WebView view, String url) {
	    		if (url.equals(getString(R.string.base_url) + "paypal/mecl/success")) {
	    			activity.finish();
	    			Intent intent = new Intent(activity, PaymentConfirmedActivity.class);
	    			intent.putExtra("offer", offer); 
	    			startActivity(intent);
	    		} else if (url.startsWith(getString(R.string.base_url) + "paypal/mecl/cancel")) {
	    			FragmentManager manager = getActivity().getSupportFragmentManager();
	    	        FragmentTransaction trans = manager.beginTransaction();
	    	        trans.remove(PayPalPaymentFragment.this);
	    	        trans.commit();
	    	        manager.popBackStack();
	    		} else if (url.startsWith(getString(R.string.base_url) + "paypal/mecl/error")) {
	    		} else if (url.startsWith(getActivity().getString(R.string.paypal_checkout_url_prefix))) {
					if(progressDialog != null) {
						progressDialog.cancel();
					}
	    		}
	    	}
	    });

	    //Load our url
	    payPalWebView.loadUrl(sb.toString());
	    
	    //The android WebView sometimes does not have focus and this affects different UI elements so we'll force the focus to work around this
	    payPalWebView.requestFocus(View.FOCUS_DOWN);
    }
	
	@Override
	public void couldNotFetchDeviceReferenceToken() {
		// Initialization failed and we didn't get a token
		deviceReferenceToken = null;
	}

	@Override
	public void receivedDeviceReferenceToken(String token) {
		// Initialization was successful
		deviceReferenceToken = token;
	}
	
	private void initLibrary() {
		if(activity != null) {
			// Main init call takes your Context, AppID, target server, and
			// PayPalListener
			PayPal.fetchDeviceReferenceTokenWithAppID(activity, getString(R.string.paypal_application_id), PAYPAL_SERVER, this);
			// Required settings:
			PayPal.getInstance().setLanguage("en_GB"); // Sets the language for the library			
		}
	}
}
