package com.example.commontask.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.example.commontask.materialcamera.internal.BaseCaptureActivity;
import com.example.commontask.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
