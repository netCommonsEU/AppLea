package com.example.commontask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.commontask.fragment.PostListFragment;
import com.github.zagum.expandicon.ExpandIconView;

public class FilterTask extends AppCompatActivity {

    RelativeLayout Layout,Layout1,Layout2;
    private View clickView,clickView1;
    Toolbar mToolBar;
    boolean isFABOpen=false;
    private ExpandIconView expandIconView,expandIconView2,expandIconView3;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


     CheckBox  checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12,checkBox13,checkBox14,checkBox15,checkBox16;
     String str,str1,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14,str15,str16;
     Button button;
     EditText editText,editText1;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener1;
    PostListFragment postListFragment;
    private static final String TAG = "FilterTask";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_inserts);


        Layout1 = (RelativeLayout) findViewById(R.id.rl2);

        button= (Button) findViewById(R.id.select);


        Layout1.setVisibility(View.GONE);

        mToolBar=(Toolbar)  findViewById(R.id.toolbar);


        expandIconView2= (ExpandIconView) findViewById(R.id.expand_icon2);


        checkBox4=(CheckBox) findViewById(R.id.ch5);
        checkBox5=(CheckBox) findViewById(R.id.ch6);
        checkBox6=(CheckBox) findViewById(R.id.ch7);
        checkBox7=(CheckBox) findViewById(R.id.ch8);
        checkBox8=(CheckBox) findViewById(R.id.ch9);
        checkBox9=(CheckBox) findViewById(R.id.ch10);
        checkBox10=(CheckBox) findViewById(R.id.ch11);
        checkBox11=(CheckBox) findViewById(R.id.ch12);
        checkBox12=(CheckBox) findViewById(R.id.ch13);
        checkBox13=(CheckBox) findViewById(R.id.ch14);
        checkBox14=(CheckBox) findViewById(R.id.ch15);
        checkBox15=(CheckBox) findViewById(R.id.ch16);
        checkBox16=(CheckBox) findViewById(R.id.ch17);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                Intent intent = new Intent(FilterTask.this, PostListFragment.class);
                startActivity(intent);

            }


        });




        clickView1 = findViewById(R.id.click1);
        clickView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isFABOpen) {
                    showFABMenu1();
                } else {
                    closeFABMenu1();
                }

            }
        });


       /* editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FilterTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });*/


        /*editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FilterTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });*/



      /*  mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                editText.setText(date);
            }
        };

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                editText1.setText(date);
            }
        };*/



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_query();
            }
        });

    }

    private void select_query() {



        if (checkBox4.isChecked()) {
            str4 = checkBox4.getText().toString();
        } else {
            str4 = " ";
        }
        if (checkBox5.isChecked()) {
            str5 = checkBox5.getText().toString();
        } else {
            str5 = " ";
        }
        if (checkBox6.isChecked()) {
            str6 = checkBox6.getText().toString();
        } else {
            str6 = " ";
        }
        if (checkBox7.isChecked()) {
            str7 = checkBox7.getText().toString();
        } else {
            str7 = " ";
        }
        if (checkBox8.isChecked()) {
            str8 = checkBox8.getText().toString();
        } else {
            str8 = " ";
        }
        if (checkBox9.isChecked()) {
            str9 = checkBox9.getText().toString();
        } else {
            str9 = " ";
        }
        if (checkBox10.isChecked()) {
            str10 = checkBox10.getText().toString();
        } else {
            str10 = " ";
        }
        if (checkBox11.isChecked()) {
            str11 = checkBox11.getText().toString();
        } else {
            str11 = " ";
        }
        if (checkBox12.isChecked()) {
            str12 = checkBox12.getText().toString();
        } else {
            str12 = " ";
        }

        if (checkBox13.isChecked()) {
            str13 = checkBox13.getText().toString();
        } else {
            str13 = " ";
        }

        if (checkBox14.isChecked()) {
            str14 = checkBox14.getText().toString();
        } else {
            str14 = " ";
        }

        if (checkBox15.isChecked()) {
            str15 = checkBox15.getText().toString();
        } else {
            str15 = " ";
        }
        if (checkBox16.isChecked()) {
            str16 = checkBox16.getText().toString();
        } else {
            str16 = " ";
        }



        Intent intent = new Intent(getApplicationContext(),PostListFragment.class);


        intent.putExtra("str4", str4);
        intent.putExtra("str5", str5);
        intent.putExtra("str6", str6);
        intent.putExtra("str7", str7);
        intent.putExtra("str8", str8);
        intent.putExtra("str9", str9);
        intent.putExtra("str10", str10);
        intent.putExtra("str11", str11);
        intent.putExtra("str12", str12);
        intent.putExtra("str13", str13);
        intent.putExtra("str14", str14);
        intent.putExtra("str15", str15);
        intent.putExtra("str16", str16);


        startActivity(intent);


    }




    private void showFABMenu1(){
        isFABOpen=true;
        Layout1.setVisibility(View.VISIBLE);
    }



    private void closeFABMenu1() {
        isFABOpen = false;
        Layout1.setVisibility(View.GONE);
    }


}
