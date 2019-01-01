package com.example.commontask;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.bumptech.glide.Glide;

import com.example.commontask.model.User;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ActivityList extends Fragment {


    RecyclerView messages;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUserDatabaseReference;
    FirebaseRecyclerAdapter<User,UserHolder> mAdapter;
    LinearLayoutManager mLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail;
    int counter=5;
    int count2=0;
    boolean c3;
    int counter1=0;
    Button ActionButton;
    ArrayList<Integer> scoresOnly;
    CardView cardView;
    BetterSpinner spinner,spinner2;
    Button button,button1;
    private boolean fabExpanded = false;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_list, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION);

        mCurrentUserDatabaseReference.keepSynced(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        messages  = (RecyclerView) rootView.findViewById(R.id.messages_list);

        spinner=(BetterSpinner) rootView.findViewById(R.id.spinner1);
        spinner2=(BetterSpinner) rootView.findViewById(R.id.spinner5);

        button1=(Button) rootView.findViewById(R.id.btnexecute);
        ActionButton=(Button) rootView.findViewById(R.id.fabSetting);

        String[] list2 = getResources().getStringArray(R.array.agerank);
        String[] list4 = getResources().getStringArray(R.array.villagesrank);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, list2);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, list4);

        ActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fabExpanded == true){
                    view.startAnimation(buttonClick);
                    closeSubMenusFab();
                }

                else{
                    view.startAnimation(buttonClick);
                    openSubMenusFab();
                }

            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();

        spinner.setAdapter(adapter2);
        spinner2.setAdapter(adapter4);

        messages.setLayoutManager(mLayoutManager);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                sort();
            }
        });

        queryrank();
        watchscoreboard();

      return rootView;

    }

      public void queryrank(){


          final Query query = mCurrentUserDatabaseReference.orderByChild("score");
          query.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(final DataSnapshot dataSnapshot) {
                  if (dataSnapshot.getValue() == null) {
                      return;
                  }

                  mAdapter = new FirebaseRecyclerAdapter<User, UserHolder>(User.class,R.layout.listview_layout,UserHolder.class,query) {


                      @Override
                      protected void populateViewHolder(UserHolder holder, User user, int position) {

                          scoresOnly = new ArrayList<Integer>();
                          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                              User score = postSnapshot.getValue(User.class);
                              scoresOnly.add(score.getScore());
                          }
                          Collections.sort(scoresOnly, Collections.reverseOrder());

                          int currentPosition = scoresOnly.indexOf(user.getScore());
                          user.setPosition(currentPosition + 1);

                          final StorageReference storageRef = FirebaseStorage.getInstance()
                                  .getReference().child(user.getProfilePicLocation());
                          holder.setName(user.getUsername());
                          holder.setMessage(user.getScore());
                          holder.mMessageField1.setText("#"+String.valueOf(user.getPosition()));


                          if(String.valueOf(user.getPosition()).equalsIgnoreCase("1")){
                              holder.imageView1.setImageResource(R.drawable.goldmedal);
                              holder.cardView.setCardBackgroundColor(Color.parseColor("#303F9F"));
                          }

                          else if(String.valueOf(user.getPosition()).equalsIgnoreCase("2")){
                              holder.imageView1.setImageResource(R.drawable.silvermedal);
                              holder.cardView.setCardBackgroundColor(Color.parseColor("#00C853"));
                          }

                          else if(String.valueOf(user.getPosition()).equalsIgnoreCase("3")){
                              holder.imageView1.setImageResource(R.drawable.bronzemedal);
                              holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
                          }


                          else{
                              holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.general));
                          }

                          if(user.getProfilePicLocation()==" ") {
                              holder.imageView.setImageResource(R.drawable.farmer);
                          }


                          Glide.with(getActivity())
                                  .using(new FirebaseImageLoader())
                                  .load(storageRef)
                                  .bitmapTransform(new CropCircleTransformation(getContext()))
                                  .into(holder.imageView);

                      }

                  };


                  messages.setAdapter(mAdapter);

              }



              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });
      }


    //closes FAB submenus
    private void closeSubMenusFab(){

        spinner.setVisibility(View.INVISIBLE);
        spinner2.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        fabExpanded = false;
    }


    //Opens FAB submenus
    private void openSubMenusFab(){
        spinner.setVisibility(View.VISIBLE);
        spinner2.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);

        fabExpanded = true;
    }



    public void sort() {


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION);

        final Query query = mCurrentUserDatabaseReference.orderByChild("score");

         FirebaseRecyclerAdapter<User,UserHolder> firebaseRecyclerAdapter;
         firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserHolder>(User.class,R.layout.listview_layout,UserHolder.class,query) {

                 @Override
                 protected void populateViewHolder(UserHolder holder, User user, int position) {


   if (user.getLocation().equalsIgnoreCase(spinner2.getText().toString()) && user.getAge().equalsIgnoreCase(spinner.getText().toString())) {


                         count2++;
                         c3 = true;

                         user.setPosition(count2);
                         holder.setName(user.getUsername());
                         holder.setMessage(user.getScore());
                         holder.mMessageField1.setText("#" + String.valueOf(user.getPosition()));


                         StorageReference storageRef = FirebaseStorage.getInstance()
                                 .getReference().child(user.getProfilePicLocation());



                         if (String.valueOf(user.getPosition()).equalsIgnoreCase("1")) {
                             holder.imageView1.setImageResource(R.drawable.goldmedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#303F9F"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("2")) {
                             holder.imageView1.setImageResource(R.drawable.silvermedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#00C853"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("3")) {
                             holder.imageView1.setImageResource(R.drawable.bronzemedal);
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
                         }
                         else {
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.global_color_green_accent));
                         }

                       /*  Glide.with(getActivity())
                                 .using(new FirebaseImageLoader())
                                 .load(storageRef)
                                 .bitmapTransform(new CropCircleTransformation(getContext()))
                                 .into(holder.imageView);*/
                     }

   if (user.getAge().equalsIgnoreCase(spinner.getText().toString()) && spinner2.getText().toString().equalsIgnoreCase("")) {


                         c3 = true;
                         count2++;

                         user.setPosition(count2);

                         StorageReference storageRef = FirebaseStorage.getInstance()
                                 .getReference().child(user.getProfilePicLocation());
                         holder.setName(user.getUsername());
                         holder.setMessage(user.getScore());
                         holder.mMessageField1.setText("#" + String.valueOf(user.getPosition()));

                         if (String.valueOf(user.getPosition()).equalsIgnoreCase("1")) {
                             holder.imageView1.setImageResource(R.drawable.goldmedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#303F9F"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("2")) {
                             holder.imageView1.setImageResource(R.drawable.silvermedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#00C853"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("3")) {
                             holder.imageView1.setImageResource(R.drawable.bronzemedal);
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
                         }
                         else {
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.global_color_green_accent));
                         }

                         Glide.with(getActivity())
                                 .using(new FirebaseImageLoader())
                                 .load(storageRef)
                                 .bitmapTransform(new CropCircleTransformation(getContext()))
                                 .into(holder.imageView);
                     }

   if (user.getLocation().equalsIgnoreCase(spinner2.getText().toString()) && spinner.getText().toString().equalsIgnoreCase("")) {


                         c3 = true;
                         count2++;


                         user.setPosition(count2);
                         holder.setName(user.getUsername());
                         holder.setMessage(user.getScore());
                         holder.mMessageField1.setText("#" + String.valueOf(user.getPosition()));

                         StorageReference storageRef = FirebaseStorage.getInstance()
                                 .getReference().child(user.getProfilePicLocation());

                         if (String.valueOf(user.getPosition()).equalsIgnoreCase("1")) {
                             holder.imageView1.setImageResource(R.drawable.goldmedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#303F9F"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("2")) {
                             holder.imageView1.setImageResource(R.drawable.silvermedal);
                             holder.cardView.setCardBackgroundColor(Color.parseColor("#00C853"));
                         }
                         else if (String.valueOf(user.getPosition()).equalsIgnoreCase("3")) {
                             holder.imageView1.setImageResource(R.drawable.bronzemedal);
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
                         }
                         else {
                             holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.global_color_green_accent));
                         }


                        Glide.with(getActivity())
                                 .using(new FirebaseImageLoader())
                                 .load(storageRef)
                                 .bitmapTransform(new CropCircleTransformation(getContext()))
                                 .into(holder.imageView);

                     }


             if (String.valueOf(user.getPosition()).equalsIgnoreCase("0")) {


                             holder.cardView.removeAllViews();
                             counter1++;


                         }


    if(spinner2.getText().toString().equalsIgnoreCase("Όλα") || spinner.getText().toString().equalsIgnoreCase("Όλα")) {


                      queryrank();


                      }



                 }


           };


        mLayoutManager=new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        messages.setHasFixedSize(true);

        messages.setLayoutManager(mLayoutManager);

        messages.setAdapter(firebaseRecyclerAdapter);
        count2 = 0;
        counter1=0;
        firebaseRecyclerAdapter.cleanup();


    }

     private void watchscoreboard() {


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmail);

        Query query = mCurrentUserDatabaseReference;


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str1= currentUserEmail = EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
             String userTestEmail = ((HashMap) dataSnapshot.getValue()).get("email").toString();
                 if(userTestEmail.equalsIgnoreCase(str1)){
                     long i = (long) ((HashMap) dataSnapshot.getValue()).get("score") + Long.valueOf(counter);
                   mCurrentUserDatabaseReference.child("score").setValue(i);
                 }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       mAdapter.cleanup();
    }



}