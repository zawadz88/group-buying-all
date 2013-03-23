package pl.edu.pw.eiti.groupbuying.android.fragment;

import java.util.HashSet;
import java.util.Set;

import pl.edu.pw.eiti.groupbuying.android.fragment.util.AlertDialogListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

//TODO remove if still unused
public class AlertDialogFragment extends DialogFragment {
	
	private Set<AlertDialogListener> listeners = new HashSet<AlertDialogListener>();

    public static AlertDialogFragment newInstance(int title, int message, int okText, AlertDialogListener listener) {
    	AlertDialogFragment frag = new AlertDialogFragment();
    	frag.listeners.add(listener);
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        args.putInt("okText", okText);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setRetainInstance(true);
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");
        int okText = getArguments().getInt("okText");

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okText,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(!listeners.isEmpty()) {
                            	for(AlertDialogListener listener : listeners) {
                            		listener.doPositiveClick();
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