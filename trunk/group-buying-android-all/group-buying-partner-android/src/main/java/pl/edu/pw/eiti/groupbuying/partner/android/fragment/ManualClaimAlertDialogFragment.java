package pl.edu.pw.eiti.groupbuying.partner.android.fragment;

import pl.edu.pw.eiti.groupbuying.partner.android.R;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.AlertDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ManualClaimAlertDialogFragment extends DialogFragment {
	
	private static final String SECURITY_KEY_TAG = "securityKey";
	private static final String COUPON_ID_TAG = "couponId";
	private AlertDialogListener listener;

    public static ManualClaimAlertDialogFragment newInstance() {
    	ManualClaimAlertDialogFragment frag = new ManualClaimAlertDialogFragment();
    	return frag;
    }
    
    public static ManualClaimAlertDialogFragment newInstance(String couponId, String securityKey) {
    	ManualClaimAlertDialogFragment frag = new ManualClaimAlertDialogFragment();
    	Bundle args = new Bundle();
    	args.putString(COUPON_ID_TAG, couponId);
    	args.putString(SECURITY_KEY_TAG, securityKey);
    	frag.setArguments(args);
    	return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setRetainInstance(true);
    }
    
 	@Override
 	public void onAttach(Activity activity) {
 		super.onAttach(activity);
 		try {
 			listener = (AlertDialogListener) activity;
         } catch (ClassCastException e) {
             throw new ClassCastException(activity.toString() + " must implement AlertDialogListener");
         }
 	}
 	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText couponIdBox = new EditText(getActivity());
        couponIdBox.setHint(R.string.coupon_id);
        couponIdBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(couponIdBox);

        final EditText couponSecretKeyBox = new EditText(getActivity());
        couponSecretKeyBox.setHint(R.string.security_key);
        layout.addView(couponSecretKeyBox);

        Bundle args = getArguments();
        if(args != null && args.containsKey(COUPON_ID_TAG) && args.containsKey(SECURITY_KEY_TAG)) {
        	couponIdBox.setText(args.getString(COUPON_ID_TAG));
        	couponSecretKeyBox.setText(args.getString(SECURITY_KEY_TAG));
        }
        
        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.claim_dialog_title)
                .setMessage(null)
                .setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(listener != null) {
                            	listener.doPositiveClick(couponIdBox.getText().toString(), couponSecretKeyBox.getText().toString());
                            }
                        }
                    }
                )
                .setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	if(listener != null) {
                            	listener.doNegativeClick();
                            }
                        }
                    }
                )
                .setView(layout)
                .setCancelable(false)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
					
                	@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        					dialog.dismiss();
        					if(listener != null) {
                            	listener.doNegativeClick();
                            }
        		        }
						return false;
					}
				})
                .create();
    }
 
	
	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
    
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
            super.onDestroyView();
    }
    
}