package com.example.commontask.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;


import com.example.commontask.Home.HomeActivity;
import com.example.commontask.R;
import com.example.commontask.ScoreboardNewFeeds;
import com.example.commontask.ui.ProfileActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class BottomNavigationViewHelperCalendar {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.instagramfarm:
                        Intent intent1 = new Intent(context.getApplicationContext(), HomeActivity.class);//ACTIVITY_NUM = 0
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                        break;



                    case R.id.scorerank:
                        Intent intent4 = new Intent(context.getApplicationContext(), ScoreboardNewFeeds.class);//ACTIVITY_NUM = 2
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent4);
                        callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                        break;

                    case R.id.weathernav:
                        Intent intent3 = new Intent(context.getApplicationContext(),  com.example.commontask.activities.MainActivity.class);//ACTIVITY_NUM = 2
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent3);
                        callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                        break;

                    case R.id.profilemain:
                        Intent intent5 = new Intent(context,  ProfileActivity.class);//ACTIVITY_NUM = 3
                        intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent5);
                        callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                        break;
                }


                return false;
            }
        });
    }
}
