package com.example.commontask;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;


public class WelcomeHome extends Fragment {

  Activity activity;
  Button button0;
  private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    activity = (Activity) context;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.frag_splash_page, container, false);
    ButterKnife.bind(this, root);

    Animation anim = AnimationUtils.loadAnimation(getActivity(),R.anim.zoom);
    TextView text = (TextView) root.findViewById(R.id.textView2);
    text.setAnimation(anim);
    button0 = (Button) root.findViewById(R.id.button15);
    button0.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, 0);
      }
    });


    return root;
  }
}
