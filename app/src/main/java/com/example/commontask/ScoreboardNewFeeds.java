package com.example.commontask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import com.example.commontask.fragment.MyPostsFragment;
import com.example.commontask.fragment.MyTopPostsFragment;


public class ScoreboardNewFeeds  {


   /* private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    Boolean changed;
    String str,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_main);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {

                    new MyPostsFragment(),
                    new MyTopPostsFragment(),
            };
            private final String[] mFragmentNames = new String[] {

                    getString(R.string.history_my_posts),
                    getString(R.string.my_top_posts)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        final int[] ICONS=new int[]{
                R.drawable.history_post,
                R.drawable.rating
        };

        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        toolbar= (Toolbar) findViewById(R.id.toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(Color.rgb(0,191,255));
        tabLayout.setClickable(true);

      //  tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            changed = true;

            str = extras.getString("str");
            str1 = extras.getString("str1");
            str2 = extras.getString("str2");
            str3 = extras.getString("str3");
            str4 = extras.getString("str4");
            str5 = extras.getString("str5");
            str6= extras.getString("str6");
            str7= extras.getString("str7");
            str8= extras.getString("str8");
            str9= extras.getString("str9");
            str10= extras.getString("str10");
            str11= extras.getString("str11");
            str12=extras.getString("str12");

        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ScoreboardNewFeeds.this, MainActivityCalendar.class));
    }*/


}
