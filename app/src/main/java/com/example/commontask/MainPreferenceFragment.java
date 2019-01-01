package com.example.commontask;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public  class MainPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String category = getArguments().getString("category");
        if (category != null) {

            if (category.equals(getString(R.string.general))) {
                addPreferencesFromResource(R.xml.prefs_general);
            }
            else if (category.equals(getString(R.string.advanced))) {
                addPreferencesFromResource(R.xml.prefs_advanced);
            }

        }

    }

}