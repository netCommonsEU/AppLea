package com.example.commontask.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.commontask.EditProfileActivity;
import com.example.commontask.MainActivityCalendar;
import com.example.commontask.MapActivity;
import com.example.commontask.Proregistry;
import com.example.commontask.R;
import com.example.commontask.SettingsPrefActivity;
import com.example.commontask.model.User;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.zagum.expandicon.ExpandIconView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import info.hoang8f.widget.FButton;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileActivity extends AppCompatActivity implements  CompoundButton.OnCheckedChangeListener {


    private Toolbar mToolBar;
    private ImageButton mphotoPickerButton;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail,currentUserName;
    private ImageView profileImage;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUserDatabaseReference;
    private DatabaseReference mCurrentUserDatabaseReference1;
    private Context mView;
    private Button button;
    FloatingActionButton fab;
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private  TextView textView,textView1,textView2,textView3,textView4,textView5,textView6,textview7;
    private DatabaseReference databaseUsers;
    private ArrayList<String> usernames=new ArrayList<>();
    String mail, name ,epo ,top ,email ,age ,job, place,use;
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    String userTestEmail, userEmail, userTestLocation, userTestAge,userTestName,userTestKind,userTestJob,userTestUse;
    Boolean changed;
    private static int RESULT_LOAD_IMAGE = 1;
    Spinner areaSpinner;
    String item2;
    ImageButton button4;
    DrawerLayout drawer;
    RelativeLayout relativeLayout;
    private Context mContext = ProfileActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private ExpandIconView expandIconView3;
    boolean isFABOpen=false;
    RelativeLayout Layout,Layout1;
    private View clickView;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private FButton buttonfield,buttonprivacy;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, ProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_final);
        mView = ProfileActivity.this;

        if (isServicesOK()) {
            init();
        }

        button = (Button) findViewById(R.id.edit);
        textView1= (TextView) findViewById(R.id.tvNumber3);
        textView = (TextView) findViewById(R.id.tvNumber1);
        textView2 = (TextView) findViewById(R.id.tvNumber4);
        textView5 = (TextView) findViewById(R.id.tvNumber6);
        textview7 = (TextView) findViewById(R.id.tvNumber2);
        Layout = (RelativeLayout) findViewById(R.id.rl);
        Layout1 = (RelativeLayout) findViewById(R.id.rl2);
        profileImage=(ImageView) findViewById(R.id.online);
        expandIconView3 = (ExpandIconView) findViewById(R.id.expand_icon3);
        Layout.setVisibility(View.GONE);
        Layout1.setVisibility(View.GONE);
        mToolBar=(Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(buttonClick);
                Intent intent = new Intent(ProfileActivity.this, MainActivityCalendar.class);
                startActivity(intent);

            }
        });


        setupFirebaseAuth();
        initializeScreen();
        openImageSelector();
        initializeUserInfo();
        getProfileInfo();
        addListenerOnButton();

        clickView = findViewById(R.id.click);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandIconView3.switchState();
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            changed = true;
            name = extras.getString("username").trim();
            job = extras.getString("job").trim();
            top = extras.getString("location").trim();
            use = extras.getString("use").trim();
            BlogViewInfoHolder viewHolder = new BlogViewInfoHolder();
            {
                if (viewHolder != null) {
                    viewHolder.setLocation(top);
                    viewHolder.setOnoma(name);
                    viewHolder.setJob(job);
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit_profile) {
            Intent intent = new Intent(getApplicationContext(), SettingsPrefActivity.class);
            startActivityForResult(intent, 0);

        }
            if (item.getItemId()  == R.id.sign_out) {
                AuthUI.getInstance()
                        .signOut(this);
                Intent intent = new Intent(this, Proregistry.class);
                startActivity(intent);
            }


        return true;

    }

    private void showFABMenu(){
        isFABOpen=true;
        Layout.setVisibility(View.VISIBLE);
        Layout1.setVisibility(View.VISIBLE);
    }
    private void closeFABMenu() {
        isFABOpen = false;
        Layout.setVisibility(View.GONE);
        Layout1.setVisibility(View.GONE);
    }

    private void getProfileInfo() {

      String currentUserEmailprof = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmailprof);
        Query query = mCurrentUserDatabaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 userTestName = ((HashMap) dataSnapshot.getValue()).get("username").toString();
                 userTestLocation = ((HashMap) dataSnapshot.getValue()).get("location").toString();
                 userTestJob = ((HashMap) dataSnapshot.getValue()).get("job").toString();
                 userTestUse = ((HashMap) dataSnapshot.getValue()).get("use").toString();
                 userEmail = ((HashMap) dataSnapshot.getValue()).get("email").toString();

                if(userTestName!=null){
                    textView.setText(userTestName);
                }

                if(userEmail!=null){
                    textView1.setText(userEmail);
                }
                if(userTestLocation!=null){
                    textView2.setText(userTestLocation);
                }
                if(userTestJob!=null){
                    textView5.setText(userTestJob);
                }
                if(userTestUse!=null){
                    textview7.setText(userTestUse);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        buttonfield.setShadowEnabled(b);
        buttonprivacy.setShadowEnabled(b);
    }


    public class BlogViewInfoHolder {

        public void setOnoma(String name) {

            textView.setText(name);

        }

        public void setJob(String job) {
            textView5.setText(job);

        }
        public void setPhone(String phone) {
            textview7.setText(phone);
        }


        public void setLocation(String location) {
            textView2.setText(location);
        }
        public void setAge(String age) {
            textView3.setText(age);
        }
        public void setUse(String use) {
            textview7.setText(use);
        }
    }

    public void addListenerOnButton() {
        //Select a specific button to bundle it with the action you want

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                startActivityForResult(intent, 0);
                view.startAnimation(buttonClick);
            }
        });


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){

        mStorage = FirebaseStorage.getInstance().getReference(); //make global
        super.onActivityResult(requestCode, requestCode, data);

        if(requestCode ==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgress.setMessage("Image uploading.....");
            mProgress.show();

            Uri uri = data.getData();
            //Keep all images for a specific chat grouped together
            final String imageLocation = "Photos/profile_picture/" + currentUserEmail;
            final String imageLocationId = imageLocation + "/" + uri.getLastPathSegment();
            final String uniqueId = UUID.randomUUID().toString();
            final StorageReference filepath = mStorage.child(imageLocation).child(uniqueId + "/profile_pic");
            final String downloadURl = filepath.getPath();
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //create a new message containing this image
                    addImageToProfile(downloadURl);

                    mProgress.dismiss();
                }
            });
        }

    }


    private void init(){

        buttonfield = (FButton) findViewById(R.id.buttonfield);

        buttonfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ProfileActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ProfileActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void addImageToProfile(final String imageLocation){


        final ImageView imageView = (ImageView) findViewById(R.id.profilePicture);


        mCurrentUserDatabaseReference
                .child("profilePicLocation").setValue(imageLocation).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child(imageLocation);

                        Glide.with(mView)
                              .using(new FirebaseImageLoader())
                                .load(storageRef)
                              .bitmapTransform(new CropCircleTransformation(mView))
                                .into(imageView);
                    }
                }
        );

    }

    public void openImageSelector(){
         fab = (FloatingActionButton) findViewById(R.id.fab);
        mProgress = new ProgressDialog(this);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                //mView = view;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivityCalendar.class));
    }



    private void initializeUserInfo(){
        final ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
        mCurrentUserDatabaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
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
                        }catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mFirebaseAuth.getCurrentUser());
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user != null){
            profileImage.setBackgroundResource(R.color.orange);
        }
        else {
            profileImage.setBackgroundResource(R.color.red);
        }
    }


    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void initializeScreen() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmail);
        }


    }


