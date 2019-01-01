package com.example.commontask.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.example.commontask.materialcamera.internal.BaseCaptureActivity;
import com.example.commontask.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
