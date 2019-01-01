package com.example.commontask.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Current {
  private String iconString;
  private long time;
  private double temperature;
  private double humidity;
  private double precipChance;
  private String summary;
  private String timeZone;

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getIcon() {
    return iconString;
  }

  public void setIcon(String icon) {
    this.iconString = icon;
  }

  public int getIconId() { return Forecast.getIconId(iconString);  }

  public long getTime() {
    return time;
  }

  public String getFormattedTime() {
    SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
    formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
    Date dateTime = new Date(getTime() * 1000); // formatter requires time in mSecs
    return formatter.format(dateTime);
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getTemperature() {
    return (int) Math.round(temperature);
  }

  private double toCelcius(double temperature) {
    return (temperature - 32) * 5/9;
  }

  public int getCelciusTemperature() { return (int) Math.round(toCelcius(temperature)); }

  public String getCelciusTemperatureWithDecimal() {
    return String.format("%.1f", toCelcius(temperature));
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  /**
   * Is it going to rain?
   * @return value as a rounded percentage
   */
  public int getPrecipChance() {
    return (int) Math.round(precipChance * 100);
  }

  public void setPrecipChance(double precipChance) {
    this.precipChance = precipChance;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
