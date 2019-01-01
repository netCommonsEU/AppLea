package com.example.commontask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class PlaceHolder extends RecyclerView.ViewHolder{

    TextView mNameField,mNameFieldPost,mNameFieldPostKInd,mNameFieldPostType;
    ImageView imageView;
    public TextView buttonViewOption;

    public PlaceHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.name);
        mNameFieldPost = (TextView) itemView.findViewById(R.id.naming);
        mNameFieldPostKInd = (TextView) itemView.findViewById(R.id.score);
        mNameFieldPostType = (TextView) itemView.findViewById(R.id.type);
        imageView= (ImageView) itemView.findViewById(R.id.icon);
        buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
    }

    public void setName(String name) {
        mNameField.setText(name);
    }
    public void setNamePost(String name) {
        mNameFieldPost.setText(name);
    }
    public void setmNameFieldPostKInd(String name) {
        mNameFieldPostKInd.setText(name);
    }
    public void setmNameFieldPostType(String type) {
        mNameFieldPostType.setText(type);
    }
}
