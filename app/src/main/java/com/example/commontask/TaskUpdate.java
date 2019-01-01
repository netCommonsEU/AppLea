package com.example.commontask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.commontask.Helper.Helper;
import com.example.commontask.fragment.PostListFragment;
import com.example.commontask.model.Place;
import com.example.commontask.model.Post;
import com.example.commontask.model.User;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class TaskUpdate extends AppCompatActivity {


    TextView textView;
    EditText editText;
    private DatabaseReference mCurrentUserDatabaseReference,mCurrentUserDatabaseReference1;

    String  number_field,name,id_place,kind ,type,number;
    int result;
    Button button,button1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    Spinner areaSpinner;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    BetterSpinner spinner;
    BetterSpinner spinner5;
    BetterSpinner spinner6;
     BetterSpinner spinner7;
    BetterSpinner spinner12;
    Spinner spinner1;
    String  item4;
    FirebaseListAdapter<Place> mAdapter;
    String currentUserEmail;
    Context mView;
   String userTestName,userTestDate;
   TextView textViewauthor,textdate,textcount,text,textView5;
   FrameLayout frameLayout;
   String item2;
   String [] output;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.task_update);
        mView = TaskUpdate.this;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        spinner5=(BetterSpinner) findViewById(R.id.spinner5);
        spinner6=(BetterSpinner) findViewById(R.id.spinner6);
         spinner7=(BetterSpinner) findViewById(R.id.spinner7);
        spinner12=(BetterSpinner) findViewById(R.id.spinner12);
        textView5=(TextView) findViewById(R.id.txtseek);
        spinner1=(Spinner) findViewById(R.id.name);
        textViewauthor=(TextView) findViewById(R.id.post_author);
        ImageView checkmark = (ImageView) findViewById(R.id.saveChanges);
        spinner=(BetterSpinner) findViewById(R.id.post_kind);
        textView=(TextView) findViewById(R.id.textViewfield);
        text=(TextView) findViewById(R.id.naming);

        textdate=(TextView) findViewById(R.id.textdatetask);
        textcount=(TextView) findViewById(R.id.count);
        frameLayout=(FrameLayout) findViewById(R.id.frame);


        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            name = extras.getString("body").trim();
            number_field  = extras.getString("body1").trim();
            kind = extras.getString("body2").trim();

            number = extras.getString("body3").trim();

            id_place= extras.getString("postid").trim();


          BlogViewInfoHolder viewHolder = new BlogViewInfoHolder();
            {

                if (viewHolder != null) {

                     output =number.split(" ");

                    viewHolder.setNamePlace(name);
                    //  viewHolder.setAge(age+",");
                    viewHolder.setType(kind);
                    viewHolder.setKind(number_field);
                    viewHolder.setNumber(number);

                }
            }
        }




        DiscreteSeekBar discreteSeekBar1 = (DiscreteSeekBar) findViewById(R.id.discrete1);

        discreteSeekBar1.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * 1;
            }
        });


        discreteSeekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                item2= String.valueOf(value);
                textView5.setText(String.valueOf(value) + " Κυβικά");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });



        String[] list2 = getResources().getStringArray(R.array.shopping_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list2);


        String[] list = getResources().getStringArray(R.array.activity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);


        spinner.setAdapter(adapter2);
        spinner5.setAdapter(adapter);



  if(kind.equalsIgnoreCase("Συγκομιδή Πρώτο Χέρι")||kind.equalsIgnoreCase("Συγκομιδή Δεύτερο Χέρι")||kind.equalsIgnoreCase("Συγκομιδή Τρίτο Χέρι")||kind.equalsIgnoreCase("Συγκομιδή Τέταρτο Χέρι")){

            spinner6.setVisibility(View.GONE);
            spinner7.setVisibility(View.GONE);


            discreteSeekBar1.setVisibility(View.GONE);


         String[] list4= getResources().getStringArray(R.array.kilos);

         ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                 android.R.layout.simple_dropdown_item_1line, list4);

         spinner12.setAdapter(adapter4);


         text.setText(output[1]);

         result= Integer.parseInt(output[1]);




        }

        else if(kind.equalsIgnoreCase("Άρδευση")){

            String[] list3 = getResources().getStringArray(R.array.duration);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list3);

            spinner6.setAdapter(adapter3);

            frameLayout.setVisibility(View.GONE);
            spinner12.setVisibility(View.GONE);


           textView5.setText(output[1]  + " Κυβικά");


        }

        else if(kind.equalsIgnoreCase("Λίπανση")){


         spinner6.setText(output[0]);

            String[] list3 = getResources().getStringArray(R.array.chemistry1);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list3);

            spinner6.setAdapter(adapter3);


           String[] list4= getResources().getStringArray(R.array.kilos);

           ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                 android.R.layout.simple_dropdown_item_1line, list4);

           spinner12.setAdapter(adapter4);

         discreteSeekBar1.setVisibility(View.GONE);
         spinner7.setVisibility(View.GONE);
         text.setText(output[1]);

         result= Integer.parseInt(output[1]);


        }

        else if(kind.equalsIgnoreCase("Ψεκασμός")){


          spinner7.setText(output[0]);

          spinner6.setText(output[1]);

            String[] list3 = getResources().getStringArray(R.array.diafilika);
            String[] list31= getResources().getStringArray(R.array.spray_type);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list3);

             ArrayAdapter<String> adapter31 = new ArrayAdapter<String>(this,
                     android.R.layout.simple_dropdown_item_1line, list31);


            spinner6.setAdapter(adapter3);

            spinner7.setAdapter(adapter31);

         String[] list4= getResources().getStringArray(R.array.liter);

         ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                 android.R.layout.simple_dropdown_item_1line, list4);

         spinner12.setAdapter(adapter4);
         discreteSeekBar1.setVisibility(View.GONE);
         text.setText(output[2]);
         result= Integer.parseInt(output[2]);



        }

        else if(kind.equalsIgnoreCase("Κλάδεμα")){


            String[] list3 = getResources().getStringArray(R.array.duration);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list3);

                 spinner6.setAdapter(adapter3);
                 textcount.setVisibility(View.GONE);
                 frameLayout.setVisibility(View.GONE);
                 discreteSeekBar1.setVisibility(View.GONE);
                 spinner12.setVisibility(View.GONE);
                 spinner7.setVisibility(View.GONE);

        }

        else if(kind.equalsIgnoreCase("Όργωμα")){


            String[] list3 = getResources().getStringArray(R.array.duration);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list3);

             spinner6.setAdapter(adapter3);
             textcount.setVisibility(View.GONE);
             frameLayout.setVisibility(View.GONE);
             discreteSeekBar1.setVisibility(View.GONE);
             spinner12.setVisibility(View.GONE);
             spinner7.setVisibility(View.GONE);

        }

     else if(kind.equalsIgnoreCase("Αραίωμα")){


         String[] list3 = getResources().getStringArray(R.array.duration);

         ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                 android.R.layout.simple_dropdown_item_1line, list3);

         spinner6.setAdapter(adapter3);

         textcount.setVisibility(View.GONE);
         frameLayout.setVisibility(View.GONE);
         discreteSeekBar1.setVisibility(View.GONE);
         spinner12.setVisibility(View.GONE);
         spinner7.setVisibility(View.GONE);

     }



     else if(kind.equalsIgnoreCase("Καλλιεργητής-Καταστροφέας")){


                String[] list3 = getResources().getStringArray(R.array.duration);


                     ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,

                            android.R.layout.simple_dropdown_item_1line, list3);

                              spinner6.setAdapter(adapter3);


                                   textcount.setVisibility(View.GONE);
                                   frameLayout.setVisibility(View.GONE);
                                   discreteSeekBar1.setVisibility(View.GONE);
                                   spinner12.setVisibility(View.GONE);
                                   spinner7.setVisibility(View.GONE);

                     }




     else if (kind.equalsIgnoreCase("Αγορά Λιπάσματος") ) {

              String[] list4= getResources().getStringArray(R.array.type_sale);

         ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
               android.R.layout.simple_dropdown_item_1line, list4);


           spinner12.setAdapter(adapter4);

              discreteSeekBar1.setVisibility(View.GONE);
              spinner6.setVisibility(View.GONE);
              spinner7.setVisibility(View.GONE);

              text.setText(output[0]);
              result= Integer.parseInt(output[0]);

     }


     else if(kind.equalsIgnoreCase("Αγορά Ραντιστικού")){


              String[] list4= getResources().getStringArray(R.array.type_sale);

              ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                                  android.R.layout.simple_dropdown_item_1line, list4);

                         spinner12.setAdapter(adapter4);


                         discreteSeekBar1.setVisibility(View.GONE);
                         spinner6.setVisibility(View.GONE);
                         spinner7.setVisibility(View.GONE);

                        text.setText(output[0]);

                        result= Integer.parseInt(output[0]);

     }



        button=(Button) findViewById(R.id.decrease);
        button1=(Button) findViewById(R.id.increase);

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





        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.LOCATIONS
                        + "/" + currentUserEmail);


        mCurrentUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();
                String currentUserEmailfinal = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

                final String uid=getUid();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {

                  if(uid.equalsIgnoreCase(currentUserEmailfinal)){

                String areaName = ((HashMap)areaSnapshot.getValue()).get("place_name").toString();
                areas.add(areaName);

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TaskUpdate.this, android.R.layout.simple_spinner_item, areas);
                  areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spinner1.setAdapter(areasAdapter);


                    }
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                item4 = spinner1.getSelectedItem().toString();
                textView.setText(item4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

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
        init();
        initializeUserInfo();


    }

    private void init() {

        String currentUserEmailprof = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

        mCurrentUserDatabaseReference1 = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmailprof);
        Query query = mCurrentUserDatabaseReference1;

        final ImageView imageView = (ImageView) findViewById(R.id.author_photo);

        query
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        userTestName = ((HashMap) dataSnapshot.getValue()).get("username").toString();
                        textViewauthor.setText(userTestName);

                        try{
                            if(user.getProfilePicLocation() != null){
                                StorageReference storageRef = FirebaseStorage.getInstance()
                                        .getReference().child(user.getProfilePicLocation());

                                Glide.with(mView)
                                        .using(new FirebaseImageLoader())
                                        .load(storageRef)
                                        .bitmapTransform(new CropCircleTransformation(mView))
                                        .into(imageView);
                            }
                        }

                        catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    private void initializeUserInfo(){

        String currentUserEmailprof = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mCurrentUserDatabaseReference1 = mFirebaseDatabase
                .getReference().child(Constants.USERS_POST
                        + "/" + currentUserEmailprof  + "/" + id_place);
        Query query = mCurrentUserDatabaseReference1;


        final ImageView imageView1 = (ImageView) findViewById(R.id.post_photo);

        query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post post=dataSnapshot.getValue(Post.class);

                        try{
                            if(post.getProfilePicLocation() != null){

                                userTestDate = ((HashMap) dataSnapshot.getValue()).get("body4").toString();
                                textdate.setText(userTestDate);

                                StorageReference storageRef1 = FirebaseStorage.getInstance()
                                        .getReference().child(post.getImagepic());

                                Glide.with(mView)
                                        .using(new FirebaseImageLoader())
                                        .load(storageRef1)
                                        .bitmapTransform(new CropCircleTransformation(mView))
                                        .into(imageView1);

                            }
                        }catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    public String getUid() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        return currentUserEmail;
    }

    private void update() {

        String finalname = textView.getText().toString();
        String kind = spinner.getText().toString();
        String type_changed = spinner5.getText().toString();
        String type_details;


       if(type_changed.equalsIgnoreCase("Άρδευση") ){
           type_details = spinner6.getText().toString() + " "+ textView5.getText().toString();
       }

       else{
           type_details = spinner6.getText().toString() + " "+ spinner7.getText().toString()+ result+" "+spinner12.getText().toString();
       }



        if (TextUtils.isEmpty(finalname) || TextUtils.isEmpty(kind) || TextUtils.isEmpty(type_changed)) {
            Helper.displayMessageToast(TaskUpdate.this, "Όλα τα πεδία πρέπει να ειναι συμπληρωμένα!");
        }

        else{

            String currentUserEmail1 = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

            mCurrentUserDatabaseReference = mFirebaseDatabase
                    .getReference().child(Constants.USERS_POST
                            + "/" + currentUserEmail1+ "/"+ id_place );

            final Query querying = mCurrentUserDatabaseReference;

            querying.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    mCurrentUserDatabaseReference.child("body").setValue(finalname);
                    mCurrentUserDatabaseReference.child("body1").setValue(kind);
                    mCurrentUserDatabaseReference.child("body2").setValue(type_changed);
                    mCurrentUserDatabaseReference.child("body3").setValue(type_details);


                    Toast.makeText(TaskUpdate.this, "ΕΠΙΤΥΧΗΣ ΕΝΗΜΕΡΩΣΗ ΚΑΤΑΧΩΡΗΣΗΣ!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),PostListFragment.class);
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }


    public class BlogViewInfoHolder {

        public void setNamePlace(String name) {

            textView.setText(name);
        }

        public void setKind(String phone)
        {
            spinner.setText(phone);
        }
        public void setType(String t)
        {
            spinner5.setText(t);
        }
        public void setNumber(String st)
        {
            text.setText(st);
        }

    }


    public void increaseInteger(View view) {
        view.startAnimation(buttonClick);
        result = result + 1;
        display(result);

    }


    public void decreaseInteger(View view) {
        view.startAnimation(buttonClick);
        if(result>0){
            result = result - 1;
            display(result);
        }
    }

    private void display(int number) {
        text.setText("" + number);
    }



}
