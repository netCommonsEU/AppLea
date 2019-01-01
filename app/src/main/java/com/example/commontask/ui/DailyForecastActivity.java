package com.example.commontask.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.commontask.Adapters.DayAdapter;
import com.example.commontask.R;
import com.example.commontask.weather.Day;
import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

  private Day[] days;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_daily_forecast);

    Intent intent = getIntent();

    Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
    days = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

    DayAdapter adapter = new DayAdapter(this, days);
    setListAdapter(adapter);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    String dayOfTheWeek = days[position].getDayOfTheWeek();
    String summary = days[position].getSummary().toLowerCase();
    String temperature = days[position].getCelciusTemperatureMaxWithDecimal();

    String message = String.format("On %s the high will be %s and the conditions will be: %s",
            dayOfTheWeek, temperature, summary);

    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}
