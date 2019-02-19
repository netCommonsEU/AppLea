package com.example.commontask.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commontask.R;
import com.example.commontask.weather.Day;

public class DayAdapter extends BaseAdapter{

  private Context context;
  private Day[] days;

  public DayAdapter(Context context, Day[] days) {
    this.context = context;
    this.days = days;
  }

  @Override
  public int getCount() {
    return days.length;
  }

  @Override
  public Object getItem(int position) {
    return days[position];
  }

  @Override
  public long getItemId(int position) {
    return 0; // Not used. Can be used to Tag items for easy reference
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    // Initialise the createView or reuse it if one already exists
    if (convertView == null) {
      // initial View
      convertView = LayoutInflater.from(context).inflate(R.layout.daily_list_item, null);
      holder = new ViewHolder();
      holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
      holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
      holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    Day day = days[position];
    holder.iconImageView.setImageResource(day.getIconId());
    holder.temperatureLabel.setText(""+day.getCelciusTemperatureMaxWithDecimal());
    holder.dayLabel.setText(day.getDayOfTheWeek());
    // Tweak the labels
    if (position == 0) {
      holder.dayLabel.setText("Today");
    }
    return convertView;
  }

  private static class ViewHolder {
    ImageView iconImageView;
    TextView temperatureLabel;
    TextView dayLabel;

  }
}
