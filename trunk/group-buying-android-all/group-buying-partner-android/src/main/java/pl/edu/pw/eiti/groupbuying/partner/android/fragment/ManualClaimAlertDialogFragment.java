package pl.edu.pw.eiti.groupbuying.partner.android.fragment;

import java.util.HashSet;
import java.util.Set;

import pl.edu.pw.eiti.groupbuying.partner.android.R;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.AlertDialogListener;
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
	
	private Set<AlertDialogListener> listeners = new HashSet<AlertDialogListener>();

    public static ManualClaimAlertDialogFragment newInstance(AlertDialogListener listener) {
    	ManualClaimAlertDialogFragment frag = new ManualClaimAlertDialogFragment();
    	frag.listeners.add(listener);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setRetainInstance(true);
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

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.claim_dialog_title)
                .setMessage(null)
                .setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(!listeners.isEmpty()) {
                            	for(AlertDialogListener listener : listeners) {
                            		listener.doPositiveClick(couponIdBox.getText().toString(), couponSecretKeyBox.getText().toString());
                            	}
                            }
                        }
                    }
                )
                .setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	if(!listeners.isEmpty()) {
                            	for(AlertDialogListener listener : listeners) {
                            		listener.doNegativeClick();
                            	}
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
        					if(!listeners.isEmpty()) {
                            	for(AlertDialogListener listener : listeners) {
                            		listener.doNegativeClick();
                            	}
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
    	listeners.clear();
    }
    
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
            super.onDestroyView();
    }
    
	public void addListener(AlertDialogListener listener) {
		listeners.add(listener);
	}

	public void removeListener(AlertDialogListener listener) {
		listeners.remove(listener);
	}
    
    
}