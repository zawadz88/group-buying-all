package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.fragment.util.AlertDialogListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class AlertDialogFragment extends DialogFragment {
	
	private AlertDialogListener listener;
	private Object relatedObject;

    public static AlertDialogFragment newInstance(int title, int message, int okText, AlertDialogListener listener, Object relatedObject) {
    	AlertDialogFragment frag = new AlertDialogFragment();
    	frag.listener = listener;
    	frag.relatedObject = relatedObject;
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        args.putInt("okText", okText);
        frag.setArguments(args);
        return frag;
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
                            listener.doPositiveClick(relatedObject);
                        }
                    }
                )
                .setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	listener.doNegativeClick(relatedObject);
                        }
                    }
                )
                .setCancelable(false)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
					
                	@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        					dialog.dismiss();
                        	listener.doNegativeClick(relatedObject);
        		        }
						return false;
					}
				})
                .create();
    }
}