package com.example.commontask.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.commontask.R;
import com.example.commontask.ui.ChatListActivity;


public class MessagesFragment extends Fragment {
    private static final String TAG = "MessagesFragment";
    private Button button;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        button = (Button) view.findViewById(R.id.btnmessage);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent =new Intent(getActivity(), ChatListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



}
