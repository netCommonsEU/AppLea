package com.example.commontask;

import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;

public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && "LGE".equalsIgnoreCase(Build.BRAND)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && "LGE".equalsIgnoreCase(Build.BRAND)) {
            openOptionsMenu();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MyActionBarActivity.ACTIVITY_CLOSED));
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MyActionBarActivity.ACTIVITY_OPENED));
        super.onResume();
    }
}
