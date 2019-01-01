package com.example.commontask;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.android.gms.maps.model.Marker;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import java.util.ArrayList;

public class EditarMark extends DialogFragment implements RecognitionListener {

    private static final String MARKER="title";
    private static final String FIELD="field";
    private static final String KIND="kind";
    private static final String TYPE="type";
    public Marker marker;
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
     EditText etTitle;
    Button button,button1;
    int result;
    TextView textView;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);



    public static EditarMark newInstance(String title,String field,String kind,String type) {
        EditarMark frag = new EditarMark();
        Bundle args = new Bundle();
        args.putString(MARKER, title);
        args.putString(FIELD, field);
        args.putString(KIND, kind);
        args.putString(TYPE, type);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.window_info, null);

        etTitle= (EditText) dialogView.findViewById(R.id.edit);
        textView=(TextView) dialogView.findViewById(R.id.naming);

        final BetterSpinner spinner= (BetterSpinner) dialogView.findViewById(R.id.spinner);
        final BetterSpinner spinner1 =(BetterSpinner) dialogView.findViewById(R.id.spinner1);

        button=(Button) dialogView.findViewById(R.id.decrease);
        button1=(Button) dialogView.findViewById(R.id.increase);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                decreaseInteger(v);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                increaseInteger(v);
            }
        });

        returnedText = (TextView) dialogView.findViewById(R.id.textView1);
        progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar1);
        toggleButton = (ToggleButton) dialogView.findViewById(R.id.micro);

        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(getContext());
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getActivity().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getActivity().getPackageName());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });



        String[] list2 = getResources().getStringArray(R.array.shopping_item);
        String[] list4 = getResources().getStringArray(R.array.type_field);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list2);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list4);

        spinner.setAdapter(adapter2);
        spinner1.setAdapter(adapter4);

        final AlertDialog dialogAlert= new AlertDialog.Builder(getActivity())
                .setTitle("Write the name that you want!")
                .setView(dialogView)
                .setPositiveButton("Yes",null)
                .setNegativeButton("Cancel",null)
                .create();

        dialogAlert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positive=dialogAlert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negative=dialogAlert.getButton(AlertDialog.BUTTON_NEGATIVE);

                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

               ((MapActivity)getActivity()).doChangeTitle(etTitle.getText().toString(), String.valueOf(result),spinner.getText().toString(),spinner1.getText().toString());
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
    }


    public void increaseInteger(View view) {
        view.startAnimation(buttonClick);
        result = result + 1;
        display(result);

    }


    public void decreaseInteger(View view) {
        view.startAnimation(buttonClick);
        if(result>0){
            result = result - 1;
            display(result);
        }
    }

    private void display(int number) {
        textView.setText("" + number);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            etTitle.setText("");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        progressBar.setIndeterminate(false);
        progressBar.setMax(15);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        etTitle.setText(errorMessage);
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {

    }

    @Override
    public void onPartialResults(Bundle arg0) {
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {

    }

    @Override
    public void onResults(Bundle results) {

        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text = result;

        etTitle.setText(text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Λάθος στην εγγραφή φωνής";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Λάθος στο παρασκήνιο";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Δεν έχετε δώσει στην εφαρμοφή δικαιώμα εγγραφής";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Λάθος δικτύο";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Το δίκτυο σταμάτησε να στέλνε";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "Δεν βρέθηκε αντιστοιχία!";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Η εφαρμογή  φωνής είναι απασχολημμένη";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "Υπάρχει λάθος στον Server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "Δεν δόθηκε είσοδος φωνής";
                break;
            default:
                message = "Δεν σε καταλαβαίνω ,ξαναπροσπάθησε";
                break;
        }
        return message;
    }

}
