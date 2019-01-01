package com.example.commontask;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.commontask.model.Privacy;

public class PrivacyArrayAdapter extends ArrayAdapter<Privacy> {

    private LayoutInflater inflater;

    public PrivacyArrayAdapter(Context context, List<Privacy> planetList) {
        super(context, R.layout.simplerow, R.id.rowTextView, planetList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Privacy planet = (Privacy) this.getItem(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simplerow, null);

            textView = (TextView) convertView.findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

            convertView.setTag(new PrivacyHolder(textView, checkBox));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Privacy planet = (Privacy) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });
        }
        // Re-use existing row view
        else {

            PrivacyHolder viewHolder = (PrivacyHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(planet);

        // Display planet data
        checkBox.setChecked(planet.isChecked());
        textView.setText(planet.getName());

        return convertView;
    }



}
