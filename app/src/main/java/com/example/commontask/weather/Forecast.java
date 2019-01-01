package com.example.commontask.weather;

import com.example.commontask.R;

public class Forecast {
  private Current current;
  private Hour[] hourlyForecasts;
  private Day[] dailyForecasts;

  public Current getCurrent() {
    return current;
  }

  public void setCurrent(Current current) {
    this.current = current;
  }

  public Hour[] getHourlyForecasts() {
    return hourlyForecasts;
  }

  public void setHourlyForecasts(Hour[] hourlyForecasts) {
    this.hourlyForecasts = hourlyForecasts;
  }

  public Day[] getDailyForecasts() {
    return dailyForecasts;
  }

  public void setDailyForecasts(Day[] dailyForecasts) {
    this.dailyForecasts = dailyForecasts;
  }

  public static int getIconId(String iconString) {
    // clear-day, clear-night, rain, snow, sleet, wind, fog,
    // cloudy, partly-cloudy-day, or partly-cloudy-night
    int iconId;
    switch (iconString) {
      case "clear-day":
        iconId = R.drawable.clear_day;
        break;
      case "clear-night":
        iconId = R.drawable.clear_night;
        break;
      case "sunny":
        iconId = R.drawable.sunny;
        break;
      case "rain":
        iconId = R.drawable.rain;
        break;
      case "snow":
        iconId = R.drawable.snow;
        break;
      case "sleet":
        iconId = R.drawable.sleet;
        break;
      case "wind":
        iconId = R.drawable.wind;
        break;
      case "fog":
        iconId = R.drawable.fog;
        break;
      case "cloudy":
        iconId = R.drawable.cloudy;
        break;
      case "partly-cloudy-day":
        iconId = R.drawable.partly_cloudy;
        break;
      case "partly-cloudy-night":
        iconId = R.drawable.cloudy_night;
        break;
      default:
        iconId = R.drawable.clear_day;
    }
    return iconId;
  }
}
