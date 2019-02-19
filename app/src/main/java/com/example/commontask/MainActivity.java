package com.example.commontask;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.commontask.Home.HomeActivity;
import com.example.commontask.model.Post;
import com.example.commontask.model.User;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout button,button1,button2,button3,button4;
    private ImageButton imageButton;
    private String currentUserEmail,currentUserName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference database, mCurrentUserDatabaseReference,mCurrentUserDatabaseReference1,mCurrentUserDatabaseReference2,mCurrentUserDatabaseReference3;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabPhoto;
    private LinearLayout layoutFabPhoto1;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "MainActivity";
     TextView textView;
    LinearLayout linearlativeLayout;
    ImageView imageView,imageView1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    RecyclerView messages,messages1;
    FirebaseRecyclerAdapter<User,UserHolder> mAdapter;
    FirebaseRecyclerAdapter<Post,UserHolder> mAdapter1;
    LinearLayoutManager mLayoutManager,mLayoutManager1;
    private FirebaseAuth mFirebaseAuth;
    int counter=5;
    int currentUserScore = 0;
    ArrayList<Integer> scoresOnly;
    ArrayList<String> dataOnly,keyOnly;
    TextView text1;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        button = (RelativeLayout) findViewById(R.id.tab2);
        button1 = (RelativeLayout) findViewById(R.id.tab3);
        button2 = (RelativeLayout) findViewById(R.id.tab4);
        button3 = (RelativeLayout) findViewById(R.id.tab);
        textView= (TextView) findViewById(R.id.textView2);
        imageView= (ImageView) findViewById(R.id.profilePicture);
        imageView1= (ImageView) findViewById(R.id.imgcalendar);
        imageButton=(ImageButton) findViewById(R.id.weatherpick);


        initializeScreen();


        ObjectAnimator colorAnim = ObjectAnimator.ofInt(text1, "textColor", Color.DKGRAY, Color.TRANSPARENT);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    initializeUserInfo();
                }
            }
        };
        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION);

        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mLayoutManager1=new LinearLayoutManager(this);
        mLayoutManager1.setReverseLayout(true);
        mLayoutManager1.setStackFromEnd(true);

        messages  = (RecyclerView) findViewById(R.id.messages_list);



        imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(view.getContext(), MainActivityCalendar.class);
                startActivityForResult(intent, 0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(view.getContext(), com.example.commontask.ui.ProfileActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(view.getContext(), ScoreboardNewFeeds.class);
                startActivityForResult(intent, 0);
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(view.getContext(), com.example.commontask.activities.MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }


    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ((wifiInfo != null && wifiInfo.isConnected())) {
            return true;
        } else {
            Snackbar.make(findViewById(R.id.rel),
                    R.string.warning_internet,
                    Snackbar.LENGTH_LONG)
                    .show();
            showDialog();
            return false;
        }
    }

   private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to activate the wifi?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(true);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initializeUserInfo(){
        final ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
    /*  String currentUserEmailfinal = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        database = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmailfinal);


                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        com.example.commontask.model.User user = dataSnapshot.getValue(com.example.commontask.model.User.class);
                        try{
                            currentUserEmail = EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
                           String userTestEmail = ((HashMap) dataSnapshot.getValue()).get("email").toString();
                            if(currentUserEmail.equalsIgnoreCase(userTestEmail)){
                                String  userTestPic = ((HashMap) dataSnapshot.getValue()).get("profilePicLocation").toString();
                                StorageReference storageRef = FirebaseStorage.getInstance()
                                        .getReference().child(userTestPic);

                                Glide.with(getApplicationContext())
                                        .using(new FirebaseImageLoader())
                                        .load(storageRef)
                                        .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                        .into(imageView);


                                TextView text = (TextView) findViewById(R.id.textView1);
                                text.setText("Score " +user.getScore()+" Points!");
                            }

                        }catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                 final String uid=getUid();
            final Query query = mCurrentUserDatabaseReference3;

   query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    return;
                }
                currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
                    String str1=currentUserEmail;

                   int counter=0;

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(uid.equalsIgnoreCase(str1)) {
                            counter++;
                        }
                    }

                    textView.setText(counter + " posts");

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        Context con;
        con = this;
        isConnected(con);
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    public String getUid() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        return currentUserEmail;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Are you sure quit from app;");
        alertDialogBuilder
                .setMessage("Press yes to quit!")
                .setCancelable(false)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initializeScreen() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        currentUserName = mFirebaseAuth.getCurrentUser().getDisplayName();
        mCurrentUserDatabaseReference2 = mFirebaseDatabase
                .getReference().child(Constants.POST_LOCATION);

        mCurrentUserDatabaseReference3 = mFirebaseDatabase
                .getReference().child(Constants.USERS_POST+ "/" + currentUserEmail);

        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmail);

         TextView text = (TextView) findViewById(R.id.textView);
        text.setText(currentUserName);
    }

}
