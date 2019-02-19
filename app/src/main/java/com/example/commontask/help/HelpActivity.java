package com.example.commontask.help;

import android.os.Bundle;

import com.example.commontask.BaseActivity;
import com.example.commontask.R;
import com.example.commontask.WeatherBaseActivity;

public class HelpActivity extends WeatherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}

	@Override
	protected void updateUI() {
	}
}
