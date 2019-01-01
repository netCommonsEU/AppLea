package com.example.commontask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.commontask.Helper.Helper;
import com.example.commontask.utils.EmailEncoding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class MapUpdate extends AppCompatActivity  {

    TextView textView;
    EditText editText;
    private DatabaseReference mCurrentUserDatabaseReference;
    String  number_field,name,id_place,kind ,type;
    int result;
    Button button,button1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    Spinner areaSpinner;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    BetterSpinner spinner,spinner1;
    BetterSpinner spinner5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.map_update);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        spinner5=(BetterSpinner) findViewById(R.id.spinner5);


        textView=(TextView) findViewById(R.id.naming);
        editText=(EditText) findViewById(R.id.name);
        button=(Button) findViewById(R.id.decrease);
        button1=(Button) findViewById(R.id.increase);

        ImageView checkmark = (ImageView) findViewById(R.id.saveChanges);

        spinner=(BetterSpinner) findViewById(R.id.spinner);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
              name = extras.getString("place_name").trim();
              number_field  = extras.getString("numbers_field").trim();
              kind = extras.getString("kind").trim();
              id_place= extras.getString("id").trim();
              type= extras.getString("type").trim();

           BlogViewInfoHolder viewHolder = new BlogViewInfoHolder();
            {
                if (viewHolder != null) {

                    viewHolder.setNamePlace(name);
                    //  viewHolder.setAge(age+",");
                    viewHolder.setNumbers_field(number_field);
                    viewHolder.setKind(kind);
                    viewHolder.setType(type);
                }
            }
        }

         result=Integer.valueOf(number_field);

        String[] list2 = getResources().getStringArray(R.array.shopping_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list2);

        String[] list = getResources().getStringArray(R.array.type_field);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);


        spinner.setAdapter(adapter);
        spinner5.setAdapter(adapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                decreaseInteger(v);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                increaseInteger(v);
            }
        });

        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void update() {

           String finalname = editText.getText().toString();
           String kind = spinner5.getText().toString();
           String number = textView.getText().toString();
           String type_changed = spinner.getText().toString();

            if (TextUtils.isEmpty(finalname) || TextUtils.isEmpty(kind) || TextUtils.isEmpty(number)|| TextUtils.isEmpty(type_changed)) {
                Helper.displayMessageToast(MapUpdate.this, "Όλα τα πεδία πρέπει να ειναι συμπληρωμένα!");
            }

            else{

           String currentUserEmail1 = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

                mCurrentUserDatabaseReference = mFirebaseDatabase
                        .getReference().child(com.example.commontask.utils.Constants.LOCATIONS
                                + "/" + currentUserEmail1+ "/"+ id_place );

                final Query querying = mCurrentUserDatabaseReference;

                querying.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                 mCurrentUserDatabaseReference.child("place_name").setValue(finalname);
                 mCurrentUserDatabaseReference.child("kind").setValue(kind);
                 mCurrentUserDatabaseReference.child("numbers_field").setValue(number);
                 mCurrentUserDatabaseReference.child("type").setValue(type_changed);

                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                    startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        }



    public void increaseInteger(View view) {

        result = result + 1;
        display(result);

    }
    public void decreaseInteger(View view) {

        if(result>=0){
            result = result - 1;
            display(result);
        }

    }


    private void display(int number) {

        textView.setText("" + number);
    }

    public class BlogViewInfoHolder {

        public void setNamePlace(String name) {

            editText.setText(name);
        }

        public void setNumbers_field(String job) {
            textView.setText(job);

        }
        public void setKind(String phone)
        {
            spinner5.setText(phone);
        }
        public void setType(String t)
        {
            spinner.setText(t);
        }

    }


}
