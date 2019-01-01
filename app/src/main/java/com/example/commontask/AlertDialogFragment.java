package com.example.commontask;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class AlertDialogFragment extends DialogFragment {

   /* private static final String AGE="title";
    private static final String LOCATION="field";

    public static AlertDialogFragment newInstance(String title,String field) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(AGE, title);
        args.putString(LOCATION, field);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);


        final BetterSpinner spinner1= (BetterSpinner) dialogView.findViewById(R.id.spinner1);
        final BetterSpinner spinner5= (BetterSpinner) dialogView.findViewById(R.id.spinner5);

        String[] list2 = getResources().getStringArray(R.array.age);
        String[] list4 = getResources().getStringArray(R.array.villages);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list2);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list4);


        spinner1.setAdapter(adapter2);
        spinner5.setAdapter(adapter4);

        final android.support.v7.app.AlertDialog dialogAlert= new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle("Filtering by")
                .setView(dialogView)
                .setPositiveButton("Ok",null)
                .setNegativeButton("Cancel",null)
                .create();

        dialogAlert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positive=dialogAlert.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                Button negative=dialogAlert.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE);

                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //((ActivityList)getParentFragment()).sort(spinner1.getText().toString(),spinner5.getText().toString());

                        dialogAlert.dismiss();
                    }
                });
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlert.dismiss();
                    }
                });

            }
        });
        return dialogAlert;
    }*/

}



