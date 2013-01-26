package pl.edu.pw.eiti.groupbuying.android;

import android.os.Bundle;
import android.util.Log;

import com.google.android.apps.analytics.easytracking.TrackedActivity;

public class HelloAndroidActivity extends TrackedActivity {

    private static String TAG = "my-android-application";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        System.out.println("HAHAHAHA");
    }

}

