package com.example.commontask.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commontask.R;
import com.example.commontask.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView,bodyView1,bodyView2,bodyView3,bodyView4;
    public ImageView imageView,imageView1;
    public TextView buttonViewOption;
    public ImageButton imageButton;
    public Button button;
    public CardView cardView;

    public PostViewHolder(View itemView) {
        super(itemView);
        imageView1= (ImageView) itemView.findViewById(R.id.post_photo);
        imageView= (ImageView) itemView.findViewById(R.id.author_photo);
        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
        bodyView1 = (TextView) itemView.findViewById(R.id.post1_body);
        bodyView2 = (TextView) itemView.findViewById(R.id.post2_body);
        bodyView3 = (TextView) itemView.findViewById(R.id.post3_body);
        bodyView4= (TextView) itemView.findViewById(R.id.post_month);
        imageButton=(ImageButton) itemView.findViewById(R.id.sendButton);
        button=(Button) itemView.findViewById(R.id.fabSetting);
        cardView=(CardView) itemView.findViewById(R.id.postcard);
        buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);

    }

    public void bindToPost(final Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);
        bodyView1.setText(post.body1);
        bodyView2.setText(post.body2);
        bodyView3.setText(post.body3);
        bodyView4.setText(post.body4);
        starView.setOnClickListener(starClickListener);
    }


}
