package com.example.commontask.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;


import com.example.commontask.R;
import com.example.commontask.Adapters.HourAdapter;
import com.example.commontask.weather.Hour;

public class HourlyForecastActivity extends Activity {

  private Hour[] hours;
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hourly_forecast);

    recyclerView=(RecyclerView)  findViewById(R.id.recyclerView);
    Intent intent = getIntent();
    Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
    hours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

    HourAdapter adapter = new HourAdapter(this, hours);
    recyclerView.setAdapter(adapter);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setHasFixedSize(true);

  }
}
