package com.example.commontask;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.commontask.model.Privacy;

public class CardViewActivity extends Activity  {


    private ListView mainListView = null;
    private Privacy [] planets = null;
    private ArrayAdapter<Privacy> listAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincheckbox);

        mainListView = (ListView) findViewById(R.id.mainListView);

        // When item is tapped, toggle checked properties of CheckBox and
        // Planet.
        mainListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View item,
                                            int position, long id) {
                        Privacy planet = listAdapter.getItem(position);
                        planet.toggleChecked();
                        PrivacyHolder viewHolder = (PrivacyHolder) item
                                .getTag();
                        viewHolder.getCheckBox().setChecked(planet.isChecked());
                    }
                });

        // Create and populate planets.
        planets = (Privacy[]) getLastNonConfigurationInstance();
        if (planets == null) {
            planets = new Privacy[] {new Privacy("Καταχωρήσεις για Άρδευση"),
                    new Privacy("Καταχωρήσεις για Κλάδεμα"),
                    new Privacy("Καταχωρήσεις για Λίπανση"),
                    new Privacy("Καταχωρήσεις για Μάζεμα"),
                    new Privacy("Καταχωρήσεις για Οργώμα"),
                    new Privacy("Καταχωρήσεις για Συγκομιδή") };
        }
        ArrayList<Privacy> planetList = new ArrayList<Privacy>();
        planetList.addAll(Arrays.asList(planets));

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PrivacyArrayAdapter(this, planetList);
        mainListView.setAdapter(listAdapter);
    }

    public Object onRetainNonConfigurationInstance() {
        return planets;
    }


}
