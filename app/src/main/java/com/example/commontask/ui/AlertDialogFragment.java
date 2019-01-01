package com.example.commontask.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.commontask.R;

public class AlertDialogFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Context context = getActivity();  // Gets the calling activity
    AlertDialog.Builder builder = new AlertDialog.Builder(context)
      .setTitle(context.getString(R.string.error_title))
      .setMessage(context.getString(R.string.http_request_failure))
      .setPositiveButton(context.getString(R.string.ok_uppercase), null);

    return builder.create();
  }
}
