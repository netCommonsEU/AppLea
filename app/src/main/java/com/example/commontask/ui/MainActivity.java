package com.example.commontask.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import butterknife.OnClick;
import com.example.commontask.R;
import com.example.commontask.weather.Current;
import com.example.commontask.weather.Day;
import com.example.commontask.weather.Forecast;
import com.example.commontask.weather.Hour;

public class MainActivity extends Activity {

  public static final String TAG = MainActivity.class.getSimpleName();
  public static final String DAILY_FORECAST = "DAILY_FORECAST";
  public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
   public TextView temperatureValue,timeLabel,humidityValue,precipValue,summaryLabel;
   public ImageView iconView,refreshView;
   public ProgressBar progressBar;

  private Forecast forecast;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_weatherforcastie);


    temperatureValue=(TextView) findViewById(R.id.temperatureLabel);
    timeLabel=(TextView) findViewById(R.id.timeLabel);
    humidityValue=(TextView) findViewById(R.id.humidityValue);
    precipValue=(TextView) findViewById(R.id.precipValue);
    summaryLabel=(TextView) findViewById(R.id.summaryLabel);
    iconView=(ImageView)  findViewById(R.id.iconImageView);
    refreshView=(ImageView) findViewById (R.id.refreshImageView);
    progressBar=(ProgressBar)findViewById(R.id.progressBar);

    progressBar.setVisibility(View.INVISIBLE);

    final double latitude = 56.4640;
    final double longitude = -2.9700;

    getForecast(latitude, longitude);

    refreshView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getForecast(latitude, longitude);
      }
    });
  }

  private void getForecast(double latitude, double longitude) {

    String apiKey = "77638bfbd0bacbeeeb549d151a80a047";
    final String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
            "/" + latitude + "," + longitude;

    if (isNetworkAvailable()) {
      toggleRefresh();
      OkHttpClient client = new OkHttpClient();
      final Request request = new Request.Builder()
              .url(forecastUrl)
              .build();

      Call call = client.newCall(request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              toggleRefresh();
            }
          });
          alertUserAboutError();
        }

        @Override
        public void onResponse(Response response) throws IOException {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              toggleRefresh();
            }
          });

          if (response.isSuccessful()) {
            String jsonData = response.body().string();
            try {
              forecast = parseForecastDetails(jsonData);
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  updateDisplay();
                }
              });
            } catch (JSONException e) {
              Toast.makeText(MainActivity.this, "Invalid Weather Data", Toast.LENGTH_LONG).show();
            }
          } else {
            alertUserAboutError();
          }
        }
      });
    } else {
      Toast.makeText(this, "No Network!", Toast.LENGTH_LONG).show();
    }
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager manager = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    boolean isAvailable = false;
    if (networkInfo != null && networkInfo.isConnected()) {
      isAvailable = true;
    }
    return isAvailable;
  }

  private Forecast parseForecastDetails(String jsonData) throws JSONException {
    Forecast forecast = new Forecast();

    forecast.setCurrent(getCurrentDetails(jsonData));
    forecast.setHourlyForecasts(getHourlyforecast(jsonData));
    forecast.setDailyForecasts(getDailyforecast(jsonData));

    return forecast;
  }

  private Hour[] getHourlyforecast(String jsonData) throws JSONException {
    JSONObject forecast = new JSONObject(jsonData);
    JSONObject hourly = forecast.getJSONObject("hourly");
    JSONArray data = hourly.getJSONArray("data");
    String timezone = forecast.getString("timezone");
    Hour[] hours = new Hour[data.length()];

    for (int i = 0; i < data.length(); i++) {
      JSONObject jsonHour = data.getJSONObject(i);
      Hour hour = new Hour();

      hour.setIcon(jsonHour.getString("icon"));
      hour.setSummary(jsonHour.getString("summary"));
      hour.setTime(jsonHour.getLong("time"));
      hour.setTemperature(jsonHour.getDouble("temperature"));
      hour.setTimezone(timezone);

      hours[i] = hour;
    }
    return hours;
  }

  private Day[] getDailyforecast(String jsonData) throws JSONException {
    JSONObject forecast = new JSONObject(jsonData);
    JSONObject daily = forecast.getJSONObject("daily");
    JSONArray data = daily.getJSONArray("data");
    String timezone = forecast.getString("timezone");
    Day[] days = new Day[data.length()];

    for(int i = 0; i < data.length(); i++) {
      JSONObject jsonDay = data.getJSONObject(i);
      Day day = new Day();

      day.setIcon(jsonDay.getString("icon"));
      day.setSummary(jsonDay.getString("summary"));
      day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
      day.setTime(jsonDay.getLong("time"));
      day.setTimezone(timezone);

      days[i] = day;
    }
    return days;
  }

  private Current getCurrentDetails(String jsonData) throws JSONException {
    JSONObject forecast = new JSONObject(jsonData);
    JSONObject currently = forecast.getJSONObject("currently");

    Current cw = new Current();
    cw.setIcon(currently.getString("icon"));
    cw.setTime(currently.getLong("time"));
    cw.setHumidity(currently.getDouble("humidity"));
    cw.setTemperature(currently.getDouble("temperature"));
    cw.setPrecipChance(currently.getDouble("precipProbability"));
    cw.setSummary(currently.getString("summary"));
    cw.setTimeZone(forecast.getString("timezone"));

    return cw;
  }

  private void updateDisplay() {
    Current currentWeather = forecast.getCurrent();

    temperatureValue.setText(currentWeather.getCelciusTemperatureWithDecimal());
    timeLabel.setText("At " + currentWeather.getFormattedTime() + " it will be");
    humidityValue.setText("" + currentWeather.getHumidity());
    precipValue.setText("" + currentWeather.getPrecipChance() + "%");
    summaryLabel.setText(currentWeather.getSummary());

    Drawable drawable = getResources().getDrawable(currentWeather.getIconId());
    iconView.setImageDrawable(drawable);
  }

  private void toggleRefresh() {
    if (progressBar.getVisibility() == View.VISIBLE) {
      progressBar.setVisibility(View.INVISIBLE);
      refreshView.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.VISIBLE);
      refreshView.setVisibility(View.INVISIBLE);
    }
  }

  private void alertUserAboutError() {
    AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.show(getFragmentManager(), "error_dialog");
  }

  @OnClick (R.id.dailyButton)
  public void startDailyActivity(View view){
    Intent intent = new Intent(this, DailyForecastActivity.class);
    intent.putExtra(DAILY_FORECAST, forecast.getDailyForecasts());

    startActivity(intent);
  }

  @OnClick (R.id.hourlyButton)
  public void startHourlyActivity() {
    Intent intent = new Intent(this, HourlyForecastActivity.class);
    intent.putExtra(HOURLY_FORECAST, forecast.getHourlyForecasts());

    startActivity(intent);
  }

}
