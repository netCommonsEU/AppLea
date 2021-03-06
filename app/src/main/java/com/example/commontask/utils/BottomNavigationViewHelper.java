package com.example.commontask.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.commontask.Home.HomeActivity;
import com.example.commontask.Likes.LikesActivity;
import com.example.commontask.Profile.ProfileActivity;
import com.example.commontask.R;
import com.example.commontask.Search.SearchActivity;
import com.example.commontask.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);


    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.ic_house:
                    Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                    context.startActivity(intent1);
                    callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                    break;

                case R.id.ic_search:
                    Intent intent2  = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 1
                    context.startActivity(intent2);
                    callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                    break;

                case R.id.ic_circle:
                    Intent intent3 = new Intent(context, ShareActivity.class);//ACTIVITY_NUM = 2
                    context.startActivity(intent3);
                    callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                    break;

                case R.id.ic_alert:
                    Intent intent4 = new Intent(context, LikesActivity.class);//ACTIVITY_NUM = 3
                    context.startActivity(intent4);
                    callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                    break;

                case R.id.ic_android:
                    Intent intent5 = new Intent(context, ProfileActivity.class);//ACTIVITY_NUM = 4
                    context.startActivity(intent5);
                    callingActivity.overridePendingTransition(R.anim.fadein, R.anim.fade_out);
                    break;
            }


            return false;
        });
    }
}
