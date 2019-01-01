package com.example.commontask.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Day implements Parcelable {
  private long time;
  private String summary;
  private double temperatureMax;
  private String icon;
  private String timezone;

  public Day() {}

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public int getTemperatureMax() {
    return (int) Math.round(temperatureMax);
  }

  private double toCelcius(double temperature) {
    return (temperature - 32) * 5/9;
  }

  public int getCelciusTemperatureMax() { return (int) Math.round(toCelcius(temperatureMax)); }

  public String getCelciusTemperatureMaxWithDecimal() {
    return String.format("%.1f", toCelcius(temperatureMax));
  }

  public void setTemperatureMax(double temperatureMax) {
    this.temperatureMax = temperatureMax;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public int getIconId(){ return Forecast.getIconId(icon); }

  public String getDayOfTheWeek() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
    formatter.setTimeZone(TimeZone.getTimeZone("timezone"));
    Date dateTime = new Date(time * 1000);
    return formatter.format(dateTime);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(summary);
    dest.writeString(timezone);
    dest.writeString(icon);
    dest.writeLong(time);
    dest.writeDouble(temperatureMax);
  }

  private Day(Parcel in) {
    // Note, order must match writetoParcel's
    summary = in.readString();
    timezone = in.readString();
    icon = in.readString();
    time = in.readLong();
    temperatureMax = in.readDouble();
  }

  public static final Creator<Day> CREATOR = new Creator<Day>() {
    @Override
    public Day createFromParcel(Parcel source) {
      return new Day(source);
    }

    @Override
    public Day[] newArray(int size) {
      return new Day[size];
    }
  };

}
