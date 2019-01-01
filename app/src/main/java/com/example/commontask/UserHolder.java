package com.example.commontask;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commontask.model.User;
import com.squareup.picasso.Picasso;

public class UserHolder extends RecyclerView.ViewHolder {
    private static final String TAG = User.class.getSimpleName();

    TextView mNameField,mNameFieldPost;
    TextView mMessageField,mMessageField12;
    TextView mMessageField1,mMessageField13;
    TextView mMessageField2,mMessageField22;
    TextView mMessageField3;
    TextView mMessageField4;
    ImageView imageView,imageView1;
    CardView cardView;

    public UserHolder(View itemView) {
        super(itemView);
        mNameFieldPost = (TextView) itemView.findViewById(R.id.name);
        mNameField = (TextView) itemView.findViewById(R.id.naming);
        mMessageField = (TextView) itemView.findViewById(R.id.score);
        mMessageField1 = (TextView) itemView.findViewById(R.id.thesiout);
        mMessageField12 = (TextView) itemView.findViewById(R.id.xwrafi);
        mMessageField13= (TextView) itemView.findViewById(R.id.task);
        mMessageField2 = (TextView) itemView.findViewById(R.id.titleposting);
        mMessageField22 = (TextView) itemView.findViewById(R.id.timestamp);
        mMessageField3 = (TextView) itemView.findViewById(R.id.posteddatabase);
        mMessageField4 = (TextView) itemView.findViewById(R.id.nameposting);
        imageView= (ImageView) itemView.findViewById(R.id.icon);
        imageView1= (ImageView) itemView.findViewById(R.id.icon1);
        cardView  = (CardView) itemView.findViewById(R.id.card);
    }

    public void setName(String name) {
        mNameField.setText(name);
    }
    public void setNamePost(String name) {
        mNameFieldPost.setText(name);
    }
    public void setNamePostLand(String name) {
        mMessageField12.setText(name);
    }
    public void setNamePostTask(String name) {
        mMessageField13.setText(name);
    }
    public void setNamePostTime(String name) {
        mMessageField22.setText(name);
    }
    public void setMessage(int message) {
        mMessageField.setText(String.valueOf(message));
    }

    public void setImage(Context context, String image) {
        Picasso.with(context).load(image).into(imageView);
    }
}