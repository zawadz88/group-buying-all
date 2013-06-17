package pl.edu.pw.eiti.groupbuying.android.nfc;

import pl.edu.pw.eiti.groupbuying.android.util.NfcMessageSentListener;
import android.app.Activity;
import android.os.Build;

public abstract class NfcHelper {
    
	protected String message;
	protected NfcMessageSentListener listener;
		
	protected NfcHelper(NfcMessageSentListener listener, String message) {
		this.message = message;
		this.listener = listener;
	}

	public static NfcHelper createInstance(NfcMessageSentListener listener, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new NfcHelperJellyBean(listener, message);
        } else {
            return new NfcHelperFroyo(listener, message);
        }
    }
	
	public abstract void initAdapter(Activity activity);
	
}
