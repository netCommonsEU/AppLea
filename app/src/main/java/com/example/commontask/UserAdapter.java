package com.example.commontask;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.example.commontask.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/* public class UserAdapter extends FirebaseRecyclerAdapter<User, UserHolder>{

  /*  private static final String TAG = UserAdapter.class.getSimpleName();
    private Context context;
    public UserAdapter(Class<User> modelClass, int modelLayout, Class<UserHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }
    @Override
    protected void populateViewHolder(UserHolder viewHolder, User user, int position) {
        viewHolder.mNameField.setText(user.getUsername());
        viewHolder.mMessageField.setText(String.valueOf(user.getScore()));
        Glide.with(context).load(user.getProfilePicLocation()).into(viewHolder.imageView);
    }



}*/
