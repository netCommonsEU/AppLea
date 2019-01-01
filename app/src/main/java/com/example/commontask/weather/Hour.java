package com.example.commontask.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Hour implements Parcelable {
  private long time;
  private String summary;
  private double temperature;
  private String icon;
  private String timezone;

  public Hour() {}

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

  public int getTemperature() { return (int) Math.round(temperature); }

  private double toCelcius(double temperature) {
    return (temperature - 32) * 5/9;
  }

  public String getTemperatureCelciusWithDecimal() {
    return String.format("%.1f", toCelcius(temperature));
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
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

  public String getHour() {
    SimpleDateFormat formatter = new SimpleDateFormat("h a");
    formatter.setTimeZone(TimeZone.getTimeZone("timezone"));
    Date dateTime = new Date(time * 1000);
    return formatter.format(dateTime);
  }

  public int getIconId(){ return Forecast.getIconId(icon); }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(summary);
    dest.writeString(icon);
    dest.writeString(timezone);
    dest.writeLong(time);
    dest.writeDouble(temperature);
  }

  private Hour(Parcel in) {
    // Note, order must match writetoParcel's
    summary = in.readString();
    icon = in.readString();
    timezone = in.readString();
    time = in.readLong();
    temperature = in.readDouble();
  }


  public static final Creator<Hour> CREATOR = new Creator<Hour>() {
    @Override
    public Hour createFromParcel(Parcel source) {
      return new Hour(source);
    }

    @Override
    public Hour[] newArray(int size) {
      return new Hour[size];
    }
  };
}
