package com.example.commontask.Likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.commontask.Home.HomeActivity;
import com.example.commontask.Profile.ProfileActivity;
import com.example.commontask.R;
import com.example.commontask.Search.SearchActivity;
import com.example.commontask.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import com.example.commontask.utils.BottomNavigationViewHelper;

public class LikesActivity extends AppCompatActivity{
    private static final String TAG = "LikesActivity";
    private static final int ACTIVITY_NUM = 3;

    private Context mContext = LikesActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started.");

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);

        com.example.commontask.BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ic_house:
                                Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);//ACTIVITY_NUM = 0
                                startActivityForResult(intent1, 0);


                                break;

                            case R.id.ic_search:
                                Intent intent2  = new Intent(getApplicationContext(), SearchActivity.class);//ACTIVITY_NUM = 1
                                startActivityForResult(intent2, 0);


                                break;

                            case R.id.ic_circle:
                                Intent intent3 = new Intent(getApplicationContext(), ShareActivity.class);//ACTIVITY_NUM = 2
                                startActivityForResult(intent3, 0);


                                break;

                            case R.id.ic_alert:
                                Intent intent4 = new Intent(getApplicationContext(), LikesActivity.class);//ACTIVITY_NUM = 3
                                startActivityForResult(intent4, 0);
                                break;

                            case R.id.ic_android:
                                Intent intent5 = new Intent(getApplicationContext(), ProfileActivity.class);//ACTIVITY_NUM = 4
                                startActivityForResult(intent5, 0);
                                break;
                        }
                        return false;
                    }
                });

    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
