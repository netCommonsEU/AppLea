package com.example.commontask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import java.util.List;

public class WelcomeSetting extends AppCompatPreferenceActivity {

  private static final String TAG = SettingsPrefActivity.class.getSimpleName();
  Toolbar toolbar;
  private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
  Context context;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //     setupActionBar();

    int horizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
    int verticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
    int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
    getListView().setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin);
    // load settings fragment
    getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    //  prepareLayout();
    //buildLegacyPreferences();
  }

  private void setupActionBar() {
    getLayoutInflater().inflate(R.layout.activity_settings, (ViewGroup)findViewById(android.R.id.content));
    Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        view.startAnimation(buttonClick);
        finish();

      }
    });
  }
  private void prepareLayout() {

    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
    View content = root.getChildAt(0);
    LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.settings_frag, null);

    root.removeAllViews();
    toolbarContainer.addView(content);
    root.addView(toolbarContainer);

    toolbar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
    toolbar.setTitle(getTitle());
    toolbar.setNavigationIcon(R.drawable.ic_backarrow);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        v.startAnimation(buttonClick);
        finish();
      }
    });

  }
  @SuppressLint("ValidFragment")
  public  class MainPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      String action = getIntent().getAction();

      if (action == null) {
        addPreferencesFromResource(R.xml.headers_legacy);

      }

      else if (action.equals(getString(R.string.general))) {
        addPreferencesFromResource(R.xml.prefs_general);

      }

      else if (action.equals(getString(R.string.advanced))) {
        addPreferencesFromResource(R.xml.prefs_advanced);
      }
    }
  }




}
