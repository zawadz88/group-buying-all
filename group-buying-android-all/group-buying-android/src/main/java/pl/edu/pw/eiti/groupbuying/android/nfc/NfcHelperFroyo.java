package pl.edu.pw.eiti.groupbuying.android.nfc;

import pl.edu.pw.eiti.groupbuying.android.util.NfcMessageSentListener;
import android.app.Activity;

public class NfcHelperFroyo extends NfcHelper{
    
	protected NfcHelperFroyo(NfcMessageSentListener listener, String message) {
		super(listener, message);
	}

	@Override
	public void initAdapter(Activity activity) {		
	}

}
