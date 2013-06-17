package pl.edu.pw.eiti.groupbuying.android.nfc;

import pl.edu.pw.eiti.groupbuying.android.util.NfcMessageSentListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class NfcHelperJellyBean extends NfcHelper implements CreateNdefMessageCallback, OnNdefPushCompleteCallback {
    		
	private NfcAdapter mNfcAdapter;

	protected NfcHelperJellyBean(NfcMessageSentListener listener, String message) {
		super(listener, message);
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
       listener.sendSuccessful();
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
        NdefMessage msg = new NdefMessage(
        		NdefRecord.createMime(
        				"application/pl.edu.pw.eiti.groupbuying.partner.android", 
        				message.getBytes()), 
        				NdefRecord.createApplicationRecord("pl.edu.pw.eiti.groupbuying.partner.android")
        );
        return msg;
	}

	@Override
	public void initAdapter(Activity activity) {
		mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter != null) {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, activity);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, activity);
        }
		
	}
	
	
}
