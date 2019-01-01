package com.example.commontask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.commontask.MainActivityCalendar;
import com.example.commontask.model.User;
import com.example.commontask.models.UserAccountSettings;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GeneralListActivity extends AppCompatActivity {
	
   /* private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.useAppLanguage();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    createUser(user);
                    Intent intent = new Intent(getApplicationContext(), MainActivityCalendar.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
               else {
                    // User is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(
                                             AuthUI.EMAIL_PROVIDER,
                                             AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Καλώς Ήλθατε!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (resultCode == RESULT_OK) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void createUser(FirebaseUser user) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final DatabaseReference usersRef = mFirebaseDatabase.getReference(Constants.USERS_LOCATION);
        final DatabaseReference userssaccountRef = mFirebaseDatabase.getReference(Constants.USERS_ACCOUNTS);
        final String user_id =currentFirebaseUser.getUid();
        final String encodedEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        final DatabaseReference userRef = usersRef.child(encodedEmail);
        final DatabaseReference useraccountRef = userssaccountRef.child(user_id);
        final String username = user.getDisplayName();
        final String profilePicLocation=" ";
        final String field=" ";
        final String finalemail= EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        final String location=" ";
        final String age=" ";
        final int score=0;
        final String kind=" ";
        final String job=" ";
        final String use=" ";

        final String description=" ";
        final String display_name=" ";
        final long followers=0;
        final long following=0;
        final long posts=0;
        final String profile_photo=" ";
        final String website=" ";

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    User newUser = new User(user_id,finalemail,username, profilePicLocation,field,location,age,score,kind,job,use);
                    userRef.setValue(newUser);
                }
                if (dataSnapshot.getValue() == null) {
                    UserAccountSettings userAccountSettings = new UserAccountSettings(description,display_name, followers,following,posts,profile_photo,username,website,user_id);
                    useraccountRef.setValue(userAccountSettings);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }*/

    }

