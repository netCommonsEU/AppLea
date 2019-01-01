package com.example.commontask;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


public class EditFilter extends DialogFragment {


    private static final String TIME="time";
    private static final String KIND="kind";
    private static final String TYPE="type";
    int result;
    TextView textView;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public static EditFilter newInstance(String time,String kind,String type) {
        EditFilter frag = new EditFilter();


        Bundle args = new Bundle();

        args.putString(TIME, time);
        args.putString(KIND, kind);
        args.putString(TYPE, type);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




}
